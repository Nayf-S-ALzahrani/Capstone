package com.example.capstone.presentation.feature_New_memory.newVoiceNoteMemoryFragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentNewVoiceNoteMemoryBinding
import com.example.capstone.utils.Timer
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "NewVoiceNoteMemoryFragment"
class NewVoiceNoteMemoryFragment : Fragment(), Timer.OnTimerTickListener, View.OnClickListener {


    private val viewModel by lazy {
        ViewModelProvider(this)[NewVoiceNoteMemoryViewModel::class.java]
    }
    private lateinit var binding: FragmentNewVoiceNoteMemoryBinding

    private val getPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){

    }

    private lateinit var recorder: MediaRecorder
    private lateinit var timer: Timer
    private lateinit var vibrator: Vibrator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewVoiceNoteMemoryBinding.inflate(layoutInflater)
        binding.cancelRecordImgBtn.visibility = View.INVISIBLE
        timer = Timer(this)
        vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.recordImgBtn.setOnClickListener(this)
        binding.cancelRecordImgBtn.setOnClickListener(this)
    }

    private fun pauseRecording(){
        Log.d(TAG, "pauseRecording")
        recorder.pause()
        viewModel.isPaused = true
        viewModel.isRecording = false
        binding.recordImgBtn.setImageResource(R.drawable.ic_mic_40x40)
        timer.pause()
    }

    private fun resumeRecording(){
        Log.d(TAG, "resumeRecording")
        recorder.resume()
        viewModel.isPaused = false
        viewModel.isRecording = true
        binding.recordImgBtn.setImageResource(R.drawable.ic_pause_svgrepo_com__1_)
        timer.start()
    }

    private fun stopRecorder(){
        timer.stop()

        recorder.apply {
            stop()
            release()
        }

        viewModel.isPaused = false
        viewModel.isRecording = false

        binding.recordImgBtn.setImageResource(R.drawable.ic_mic_40x40)
        binding.timerTxtView.setText(R.string.init_timer)

        viewModel.amplitudes = binding.waveformView.clear()
    }

    private fun startRecording(){
        if (PackageManager.PERMISSION_GRANTED != requireActivity()?.let {
                ContextCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO)
            }){
            getPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            return
        }
        Log.d(TAG, "startRecording")
        binding.cancelRecordImgBtn.visibility = View.VISIBLE
        recorder = MediaRecorder()
        viewModel.dirPath = "${requireContext().externalCacheDir?.absolutePath}/"
        var simpleDateFormat = SimpleDateFormat("yyy.MM.DD_hh.mm.ss")
        var date = simpleDateFormat.format(Date())

        viewModel.fileName = "audio_record_$date"
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("${viewModel.dirPath}${viewModel.fileName}.mp3")
            try {
                prepare()
            }catch (e: IOException){
                Log.e(TAG, "startRecording: ${e.localizedMessage}", )
            }
            start()
        }
        binding.recordImgBtn.setImageResource(R.drawable.ic_pause_svgrepo_com__1_)
        viewModel.isRecording = true
        viewModel.isPaused = false
        timer.start()
    }

    override fun onTimerTick(duration: String) {
        binding.timerTxtView.text = duration
        binding.waveformView.addAmplitude(recorder.maxAmplitude.toFloat())
    }

    override fun onClick(v: View?) {
        when(v){
            binding.recordImgBtn -> {
                when {
                    viewModel.isPaused -> resumeRecording()
                    viewModel.isRecording -> pauseRecording()
                    else -> startRecording()
                }
                if (PackageManager.PERMISSION_GRANTED != requireActivity()?.let {
                        ContextCompat.checkSelfPermission(it, Manifest.permission.VIBRATE)
                    }){
                    getPermissionLauncher.launch(Manifest.permission.VIBRATE)
                }else{
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }
            binding.cancelRecordImgBtn -> {
                stopRecorder()
                File("$viewModel.dirPath$viewModel.fileName.mp3").delete()
            }
        }
    }

}
package com.example.capstone.presentation.feature_New_memory.newImageVideoMemoryFragment

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import com.example.capstone.R
import com.example.capstone.utils.Constants
import com.example.capstone.databinding.NewImageVideoMemoryFragmentBinding
import java.io.FileNotFoundException
import android.graphics.BitmapFactory
import android.provider.OpenableColumns
import android.view.*
import android.widget.MediaController
import java.io.InputStream


private const val TAG = "NewMemoryFragment"

class NewImageVideoMemoryFragment : Fragment(), View.OnClickListener{


    private val viewModel by lazy {
        ViewModelProvider(this)[NewImageVideoMemoryViewModel::class.java]
    }

    private lateinit var binding: NewImageVideoMemoryFragmentBinding
    private lateinit var selectedImageBitmap: Bitmap
//    private val mediaPlayer = MediaPlayer()

    private val openGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        when (viewModel.mediaType) {
            Constants.IMAGE_MEMORY -> {
                viewModel.mediaUri = it
                updatePhotoUI()
            }
            Constants.VIDEO_MEMORY -> {
                viewModel.mediaUri = it
                updateVideoUI()
            }
            else -> {
                throw IllegalStateException("Selected media type is invalid.")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewImageVideoMemoryFragmentBinding.inflate(layoutInflater)

        initialViewsVisibility()
        checkMediaType()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.uploadMemoryImgView.setOnClickListener(this)
        binding.uploadMemoryVideoView.setOnClickListener(this)
        binding.previousBtn.setOnClickListener(this)
        binding.nextBtn.setOnClickListener(this)
    }

    private fun initialViewsVisibility() {
        binding.previousBtn.visibility = View.INVISIBLE
        binding.nextBtn.visibility = View.INVISIBLE
    }

    private fun checkMediaType() {
        when (viewModel.mediaType) {
            Constants.IMAGE_MEMORY -> {
                binding.uploadMemoryVideoView.visibility = View.GONE
            }
            Constants.VIDEO_MEMORY -> {
                binding.uploadMemoryImgView.visibility = View.GONE
            }
            else -> {
                throw IllegalStateException("Selected media type is invalid.")
            }
        }
    }

    private fun updatePhotoUI() {
        try {
            val imageStream: InputStream =
                requireContext().contentResolver.openInputStream(viewModel.mediaUri!!)!!
            selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
            binding.uploadMemoryImgView.setImageBitmap(selectedImageBitmap)
            binding.uploadLabelTxtView.visibility = View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "updateVideoUI: ${e.printStackTrace()}")
        }

        Log.d(TAG, "updatePhotoUI: ${context?.getFileName(viewModel.mediaUri!!)}")
    }

    private fun updateVideoUI() {
        binding.uploadLabelTxtView.visibility = View.GONE
        binding.nextBtn.visibility = View.VISIBLE
        binding.uploadMemoryVideoView.apply {
            setMediaController(MediaController(requireContext()))
            setVideoURI(viewModel.mediaUri)
            requestFocus()
            start()
        }
        Log.d(TAG, "updateVideoUI: ${context?.getFileName(viewModel.mediaUri!!)}")
    }

    private fun Context.getFileName(uri: Uri): String? = when (uri.scheme) {
        ContentResolver.SCHEME_CONTENT -> getCursorContent(uri)
        else -> null
    }

    @SuppressLint("Range")
    private fun Context.getCursorContent(uri: Uri): String? = try {
        contentResolver.query(uri, null, null, null, null)?.let { cursor ->
            cursor.run {
                if (moveToFirst()) getString(getColumnIndex(OpenableColumns.DISPLAY_NAME))
                else null
            }.also { cursor.close() }
        }
    } catch (e: Exception) {
        null
    }

    override fun onClick(v: View?) {
        when(v){
            binding.uploadMemoryImgView -> {
                openGalleryLauncher.launch("image/*")
            }
            binding.uploadMemoryVideoView -> {
                openGalleryLauncher.launch("video/*")
            }
            binding.previousBtn -> {
                if (binding.flipper.currentView.id == R.id.memoryDetailContainer) {
                    binding.nextBtn.visibility = View.INVISIBLE
                }
                binding.flipper.showPrevious()
            }
            binding.nextBtn -> {
                if (binding.flipper.currentView.id == R.id.memoryDetailContainer) {
                    binding.nestedFlipper.showNext()
                } else {
                    when(viewModel.mediaType){
                        Constants.IMAGE_MEMORY -> {
                            binding.previewVideoMemoryVideoView.visibility = View.INVISIBLE
                            binding.previewImageMemoryImgView.setImageBitmap(selectedImageBitmap)
                        }
                        Constants.VIDEO_MEMORY -> {
                            binding.previewImageMemoryImgView.visibility = View.INVISIBLE
                            binding.previewVideoMemoryVideoView.apply {
                                setMediaController(MediaController(requireContext()))
                                setVideoURI(viewModel.mediaUri)
                                requestFocus()
                                start()
                            }
                        }
                    }

                    binding.flipper.showNext()
                }
            }
        }
    }
}


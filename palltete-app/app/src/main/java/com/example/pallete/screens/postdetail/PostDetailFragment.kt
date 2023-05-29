package com.example.pallete.screens.postdetail

import android.content.ContentValues
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import com.example.pallete.MAIN
import com.example.pallete.R
import com.example.pallete.auth.AuthManager
import com.example.pallete.databinding.FragmentPostDetailBinding
import com.example.pallete.posts.PostManager
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Date

class PostDetailFragment : Fragment() {
    lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel
    private lateinit var adapter: CommentAdapter

    private val PERMISSION_REQUEST_CODE = 100

    private lateinit var starImageViews: List<ImageView>

    private fun checkPermission(): Boolean {
        val permissionResult = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionResult == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun downloadImage(binding: FragmentPostDetailBinding) {
        val imageView = requireView().findViewById<ImageView>(R.id.img_picture)
        val drawable = imageView.drawable

        GlobalScope.launch {
            val bitmap = (drawable as BitmapDrawable).bitmap

            saveImageToPictures(bitmap)
        }
    }

    private suspend fun saveImageToPictures(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val filename = "pictures" + System.currentTimeMillis() + ".jpg"
            val picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(picturesDirectory, filename)

            var outputStream: OutputStream? = null
            try {
                outputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.TITLE, filename)
                    put(MediaStore.Images.Media.DESCRIPTION, "Image saved from Pallete")
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                }

                requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }finally {
                outputStream?.close()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)
        val toolbar = binding.toolbar.toolbarActionbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        MAIN.navController.enableOnBackPressed(true)
        toolbar.setupWithNavController(MAIN.navController)

        starImageViews = listOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5,
            binding.star6,
            binding.star7,
            binding.star8,
            binding.star9,
            binding.star10,
        )

        for (i in starImageViews.indices) {
            starImageViews[i].setOnClickListener {
                rateStars(i)
            }
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PostDetailViewModel::class.java]
        adapter = CommentAdapter()
        binding.rvComments.adapter = adapter
        viewModel.post.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.imagePath).into(binding.imgPicture)
            binding.txtTitle.text = it.title
            binding.txtRate.text = it.rate.toString()
            binding.txtDescription.text= it.description
            if (it.authorId == AuthManager.getUser().userId) {
                binding.imgEdit.visibility = View.VISIBLE
            } else {
                binding.imgEdit.visibility = View.GONE
            }
        })
        viewModel.authorName.observe(viewLifecycleOwner, Observer {
            binding.txtAuthor.text = it
        })

        viewModel.comments.observe(viewLifecycleOwner, Observer {
            adapter.setPostsData(it)
        })
        viewModel.commentators.observe(viewLifecycleOwner, Observer {
            adapter.setUserData(it)
        })

        binding.imgEdit.setOnClickListener {
            PostManager.setPost(viewModel.post.value!!)
            MAIN.navController.navigate(R.id.action_postDetailFragment_to_editPostFragment)
        }

        binding.btnDownload.setOnClickListener {
            if (checkPermission()) {
                downloadImage(binding)
            } else {
                requestPermission()
            }
            Toast.makeText(requireContext(), "Image downloaded", Toast.LENGTH_LONG).show()
        }

        binding.btnComment.setOnClickListener {
            showAddCommentDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImage(binding)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun rateStars(selectedIndex: Int) {
        for (i in starImageViews.indices) {
            if (i <= selectedIndex) {
                starImageViews[i].setImageResource(R.drawable.icon_star_filled)
            } else {
                starImageViews[i].setImageResource(R.drawable.icon_star)
            }
        }
        val rate = selectedIndex + 1
        binding.txtRate.text = rate.toString()
    }

    private fun showAddCommentDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_comment, null)
        val editTextComment = dialogView.findViewById<EditText>(R.id.editTextComment)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle("Add comment")
        alertDialogBuilder.setPositiveButton("Add") { dialog: DialogInterface, _: Int ->
            val comment = editTextComment.text.toString()
            // Выполните необходимые действия для сохранения комментария
            saveComment(comment)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
//        val buttonAdd = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//        val buttonCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//
//        buttonAdd.setOnClickListener {
//            val comment = editTextComment.text.toString()
//            saveComment(comment)
//            alertDialog.dismiss()
//        }
//
//        buttonCancel.setOnClickListener {
//            alertDialog.dismiss()
//        }
    }

    private fun saveComment(comment: String) {
        viewModel.addComment(comment)
    }
}
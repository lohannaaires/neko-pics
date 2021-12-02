package com.lohanna.nekopics.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lohanna.nekopics.R
import com.lohanna.nekopics.application.NekoPicsApp
import com.lohanna.nekopics.databinding.FragmentCatImageBinding
import com.lohanna.nekopics.utils.loadImageFromGlide
import com.lohanna.nekopics.utils.showCustomActionBar
import com.lohanna.nekopics.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CatImageFragment: Fragment(R.layout.fragment_cat_image) {

    val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentCatImageBinding

    private val args: CatImageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCustomActionBar((activity as AppCompatActivity))

        binding = FragmentCatImageBinding.bind(view)

        val cat = args.catData

        binding.imageView.loadImageFromGlide(cat?.url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                when(NekoPicsApp.getInternetStatus()) {
                    true -> {
                        share()
                    }
                    false -> {
                        Toast.makeText(context, R.string.internet_message, Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun share() {
        val cat = args.catData
        val image: Bitmap? = getBitmapFromView(binding.imageView)
        val share = Intent(Intent.ACTION_SEND)

        share.type = "image/"
        share.putExtra(Intent.EXTRA_STREAM, getImageURI(context, image!!))
        share.putExtra(Intent.EXTRA_TEXT, cat?.url)
        startActivity(Intent.createChooser(share, "Share via"))
    }

    private fun getBitmapFromView(view: ImageView): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }


    private fun getImageURI(inContext: Context?, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()

        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        @Suppress("DEPRECATION")
        val path = MediaStore.Images.Media.insertImage(inContext?.contentResolver, inImage, "Title", null)

        return Uri.parse(path)
    }

}
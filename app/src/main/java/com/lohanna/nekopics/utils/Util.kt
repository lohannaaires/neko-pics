package com.lohanna.nekopics.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lohanna.nekopics.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun showCustomActionBar(activity: AppCompatActivity) {
    activity.supportActionBar?.show()
    activity.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    activity.supportActionBar?.setCustomView(R.layout.action_bar)
}

fun ImageView.loadImageFromGlide(url: String?) {
    if(url!=null) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_baseline_broken_image_24)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}

fun Fragment.logData(message:String) {
    Log.d(this.javaClass.simpleName, "Log -->: $message")
}

fun checkForInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }

    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}
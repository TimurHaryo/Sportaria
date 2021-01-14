package id.train.sportaria.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun <T> View.glide(poster: T, holder: ImageView) {
    try {
        Glide.with(this)
            .load(poster)
            .into(holder)
    } catch (ignored: Throwable) {
    }
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, message, length).apply { show() }
}
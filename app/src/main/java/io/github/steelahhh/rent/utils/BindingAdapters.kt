package io.github.steelahhh.rent.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/*
 * Created by Alexander Efimenko on 5/11/18.
 */

@BindingAdapter("app:image")
fun setImage(imageView: AppCompatImageView, url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(imageView)
            .load(url)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
}
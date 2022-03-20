package com.example.noodoe.util

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.noodoe.R

@BindingAdapter("imageUrl")
fun ImageView.glideLoadImg(imgUrl: String?) {
    imgUrl?.let {
        Glide.with(this.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .circleCrop()
            .into(this)
    }
}

@BindingAdapter("imgLink")
fun ImageView.setImgLink(url: String) {
    this.setOnClickListener {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }
}
package com.kuchmenko.photoapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class Photo_Activity : AppCompatActivity() {
    private lateinit var photoView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)

        photoView = findViewById(R.id.imageView)

        val url = intent.getStringExtra("url")

        Glide.with(this)
            .load(url)
            .into(photoView)
    }
}
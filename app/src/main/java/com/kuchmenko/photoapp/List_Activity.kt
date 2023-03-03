package com.kuchmenko.photoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import com.kuchmenko.photoapp.api.UnsplashService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class List_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    object Constants {
        const val BASE_URL = "https://api.unsplash.com/"
        const val ACCESS_KEY = "cf49c08b444ff4cb9e4d126b7e9f7513ba1ee58de7906e4360afc1a33d1bf4c0]"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ImageAdapter(emptyList())
        recyclerView.adapter = adapter

        val api = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashService::class.java)

        api.getPhotos().enqueue(object : Callback<List<UnsplashPhoto>> {
            override fun onResponse(
                call: Call<List<UnsplashPhoto>>,
                response: Response<List<UnsplashPhoto>>
            ) {
                if (response.isSuccessful) {
                    val photos = response.body() ?: emptyList()
                    adapter.setData(photos)
                }
            }

            override fun onFailure(call: Call<List<UnsplashPhoto>>, t: Throwable) {
                Log.e("ListActivity", "Failed to fetch photos", t)
            }
        })
    }

    private inner class ImageAdapter(private var data: List<UnsplashPhoto>) :
        RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val photo = data[position]
            holder.titleTextView.text = photo.title
            holder.authorTextView.text = photo.authorName
            Glide.with(holder.imageView)
                .load(photo.url)
                .into(holder.imageView)
            holder.itemView.setOnClickListener {
                val intent = Intent(this@List_Activity,Photo_Activity::class.java)
                intent.putExtra("url", photo.url)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun setData(data: List<UnsplashPhoto>) {
            this.data = data
            notifyDataSetChanged()
        }

        private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
        }
    }



private fun Intent.putExtra(s: String, url: Any) {
    TODO("Not yet implemented")
}

private fun Any.enqueue(callback: Callback<List<UnsplashPhoto>>) {

}


data class UnsplashPhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("urls")
    val urls: UnsplashPhotoUrls,
    @SerializedName("user")
    val user: UnsplashPhotoUser
) {
    val authorName: CharSequence? = null
    val title: CharSequence?
        get() {
            TODO()
        }
    val url: Any = TODO()
}

data class UnsplashPhotoUrls(
    @SerializedName("raw")
    val raw: String,
    @SerializedName("full")
    val full: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String
)

data class UnsplashPhotoUser(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String
)



}

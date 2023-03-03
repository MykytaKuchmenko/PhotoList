package com.kuchmenko.photoapp.api

import android.provider.ContactsContract
import com.kuchmenko.photoapp.List_Activity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UnsplashService {

    interface UnsplashService {
        @GET("photos/{id}")
        fun getPhoto(@Path("id") id: String): Call<List_Activity.UnsplashPhoto>
    }

    @GET("photos")
    fun getPhotos(): Call<List<ContactsContract.CommonDataKinds.Photo>>

    companion object {
        operator fun invoke(): UnsplashService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(List_Activity.Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(UnsplashService::class.java)
        }
    }
}

    fun getPhoto(id: String): Call<List_Activity.UnsplashPhoto> {
    return unsplashService.getPhoto(id)
}




companion object {
    const val BASE_URL = "https://api.unsplash.com/"
}

}
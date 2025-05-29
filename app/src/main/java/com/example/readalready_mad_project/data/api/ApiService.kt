package com.example.readalready_mad_project.data.api

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleBookApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NYTBookApi


interface GoogleBookApiService{
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,

        ): BookResponse
}

interface  NYTBookApiService {
    @GET("lists/overview.json")
    suspend fun getTrendingBooks(
        @Query("api-key") apiKey: String
    ): NYTResponse
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CACHE_SIZE_BYTES = 10L * 1024L * 1024L // 10 MB

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDir = File(context.cacheDir, "http_cache")
        return Cache(cacheDir, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=300")
                    .build()
            }
            .build()
    }


    @GoogleBookApi
    @Provides
    @Singleton
    fun provideRetrofitGoogleApi(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @NYTBookApi
    @Provides
    @Singleton
    fun provideRetrofitNYTBookApi(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/books/v3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @GoogleBookApi
    @Provides
    fun provideGoogleBookApiService(@GoogleBookApi retrofit: Retrofit): GoogleBookApiService {
        return retrofit.create(GoogleBookApiService::class.java)
    }

    @NYTBookApi
    @Provides
    fun provideNYTBookApiService(@NYTBookApi retrofit: Retrofit): NYTBookApiService {
        return retrofit.create(NYTBookApiService::class.java)
    }
}


data class BookResponse(
    val kind: String?,
    val totalItems: Int?,
    val items: List<BookItem>?
)

data class BookItem(
    val id: String?,
    val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val pageCount: Int?,
    val categories: List<String>?,
    val averageRating: Double?,
    val ratingsCount: Int?,
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)


data class NYTResponse(
    val results: Results?
)

data class Results(
    val lists: List<ListsItem>?
)

data class ListsItem(
    val books: List<Book>?
)

data class Book(
    val title: String?
)

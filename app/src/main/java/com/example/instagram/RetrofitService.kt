package com.example.instagram

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

class User(
    val username: String,
    val token: String,
    val id: Int
)

class UserInfo(
    val id: Int, val
    username: String,
    val profile: OwnerProfile
)

class Post(
    val content: String, image: File
)

class InstaPost(
    val id: Int,
    val content: String,
    val image: String,
    val owner_profile: OwnerProfile
)

class OwnerProfile(
    val username: String,
    val image: String?
)


interface RetrofitService {
    @Multipart
    @PUT("user/profile/{user_id}/")
    fun changeProfile(
        @Path("user_id") userId: Int,
        @HeaderMap headers: Map<String, String>,
        @Part image: MultipartBody.Part,
        @Part("user") user: RequestBody,
    ): Call<Any>

    @GET("user/userInfo/")
    fun getUserInfo(
        @HeaderMap headers: Map<String, String>,
    ): Call<UserInfo>

    @Multipart
    @POST("instagram/post/")
    fun uploadPost(
        @HeaderMap headers: Map<String, String>,
        @Part image: MultipartBody.Part,
        @Part("content") content: RequestBody
    ): Call<Any>

    @POST("instagram/post/like/{post_id}/")
    fun postLike(@Path("post_id") post_id: Int): Call<Any>


    @POST("user/signup/")
    @FormUrlEncoded
    fun instaJoin(@FieldMap params: HashMap<String, Any>): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun instaLogin(@FieldMap params: HashMap<String, Any>): Call<User>

    @GET("instagram/post/list/all/")
    fun getInstagramPosts(
    ): Call<ArrayList<InstaPost>>
}
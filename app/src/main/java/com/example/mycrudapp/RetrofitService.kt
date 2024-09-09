package com.example.mycrudapp

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
import retrofit2.http.Query
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

class ToDo(
    val id: Int,
    val content: String,
    val is_complete: Boolean,
    val created: String
)

interface RetrofitService {
    @GET("to-do/search/")
    fun searchToDoList(
        @HeaderMap headers: Map<String, String>,
        @Query("keyword") keyword: String
    ): Call<ArrayList<ToDo>>

    @PUT("to-do/complete/{todoId}")
    fun changeTodoComplete(
        @HeaderMap headers: Map<String, String>,
        @Path("todoId") todoId: Int
    ): Call<Any>

    @GET("to-do/")
    fun getTodoList(
        @HeaderMap headers: Map<String, String>,
    ): Call<ArrayList<ToDo>>

    @POST("to-do/")
    @FormUrlEncoded
    fun makeTodo(
        @HeaderMap headers: Map<String, String>,
        @FieldMap params: HashMap<String, Any>
    ): Call<Any>

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
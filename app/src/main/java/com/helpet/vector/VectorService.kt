package com.helpet.vector

import android.content.IntentFilter.create
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.URI.create

//강아지 눈진단
interface VectorService {
    @Multipart
    @POST("/api/diagnosis/dog/eye")
    fun vectorResult(
        @Part postImg: MultipartBody.Part,
//        @Part("date") date: RequestBody,
//        @Part("petname") petname: RequestBody,
//        @Part("username") username: RequestBody,

    ): Call<ResponseDto>
}
//고양이 눈 진단
interface catVectorService {
    @Multipart
    @POST("/api/diagnosis/cat/eye")
    fun catvectorResult(
        @Part postImg: MultipartBody.Part,
//        @Part("date") date: RequestBody,
//        @Part("petname") petname: RequestBody,
//        @Part("username") username: RequestBody,

    ): Call<ResponseDto>
}
//강아지 피부 진단
interface dogSkinVectService {
    @Multipart
    @POST("/api/dog/skin")
    fun dogskinvectResult(
        @Part postImg: MultipartBody.Part,
//        @Part("date") date: RequestBody,
//        @Part("petname") petname: RequestBody,
//        @Part("username") username: RequestBody,

    ): Call<ResponseDto>
}

//반려동물 등록할때 보내는 요청
interface PetService {
    @FormUrlEncoded
    @POST("/api/pet/register")
    fun PetRegister(
//        @Part petImg : MultipartBody.Part,
//        @Field ("petImg") petImg: String,
        @Field("userId") userId: String,
        @Field("petSpecies") petSpecies : String,
        @Field("petName") petName: String,
        @Field("petAge") petAge: String,
        @Field("petBirth") petBirth: String,
        @Field("petGender") petGender: String
    ):Call<PetResponseDto>
}

//사용자가 가지고있는 반려동물 리스트 받아오는 요청
interface GetPetService{
    @FormUrlEncoded
//    @POST("/api/:userId")
    @POST("/api/pet/test")

    fun getPetRegister(
    @Field("userId") userId: String
    ):Call<petListResponseDTO>
}

//진단 결과 저장하는 요청
interface VectResultService{
    @Multipart
    @POST("")
    fun vectResultService(
        @Part("userId") userId: String,
        @Part("petName") petName: String,
//        @Part("vectImg") vectImg : String,
        @Part("vectDate") vectDate :String,
        @Part("vectName") vectName :String,
        @Part("vectProb") vectProb :Double,
        @Part("vectContent") vectContent :String

    ):Call<VectResultResponseDTO>
}

//진단 결과 마이펫에서 불러오는 요청
interface MyPetVectService{
    @FormUrlEncoded
    @POST("")
    fun myPetService(
        @Part("userId") userId: String,
        @Part("petName") petName: String,
    ):Call<MpVectResponseDTO>
}

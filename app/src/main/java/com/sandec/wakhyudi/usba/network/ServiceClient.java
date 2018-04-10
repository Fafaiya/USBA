package com.sandec.wakhyudi.usba.network;

import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.model.Response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wakhyudi on 17/02/18.
 */

public interface ServiceClient {
  //ini untuk mendapatkan soal
  @GET("exec")
    Call<Response> getListSoal(@Query("action") String action,
                               @Query("sheetName") String sheetName);

  //ini untuk mendapatkan hasil jawaban
  @GET("exec")
    Call<ResponServer> getResultExam(@Query("action") String action,
                                     @Query("sheetName") String sheetName,
                                     @Query("nis") String nis);

  //ini untuk mengirim jawaban
  @FormUrlEncoded
  @POST("exec")
    Call<ResponServer> sendAnswer(@Field(value = "action", encoded = true) String action,
                                  @Field(value = "sheetName", encoded = true) String sheetName,
                                  @Field(value = "nis", encoded = true) String nis,
                                  @Field(value = "soal", encoded = true)List<Integer> listSoal,
                                  @Field(value = "jawaban", encoded = true)List<String> listJawaban);

  //ini untuk melakukan login
  @POST("exec")
  Call<ResponServer> login(@Query("action")String action,
                                @Query("sheetName")String sheetName,
                                @Query("nis")String nis,
                                @Query("pass")String pass);

  //ini untuk pengecekan token
  @POST("exec")
  Call<ResponServer> token(@Query("action")String action,
                           @Query("sheetName")String sheetName,
                           @Query("nis")String nis,
                           @Query("token")String token);




}

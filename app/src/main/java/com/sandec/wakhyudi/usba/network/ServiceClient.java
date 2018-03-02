package com.sandec.wakhyudi.usba.network;

import com.sandec.wakhyudi.usba.model.ResponServer;
import com.sandec.wakhyudi.usba.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wakhyudi on 17/02/18.
 */

public interface ServiceClient {
  @GET("exec")
    Call<Response> getListSoal(@Query("action") String action,
                               @Query("sheetName") String sheetName);

  @POST("exec")
    Call<ResponServer> sendAnswer(@Query("action") String action,
                                  @Query("sheetName") String sheetName,
                                  @Query("soal")List<String> listSoal,
                                  @Query("jawaban")List<String> listJawaban);

  @POST("exec")
  Call<ResponServer> login(@Query("action")String action,
                                @Query("sheetName")String sheetName,
                                @Query("nis")String nis,
                                @Query("pass")String pass);

  @POST("exec")
  Call<ResponServer> token(@Query("action")String action,
                           @Query("sheetName")String sheetName,
                           @Query("nis")String nis,
                           @Query("token")String token);




}

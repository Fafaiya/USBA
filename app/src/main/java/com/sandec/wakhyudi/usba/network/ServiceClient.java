package com.sandec.wakhyudi.usba.network;

import com.sandec.wakhyudi.usba.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wakhyudi on 17/02/18.
 */

public interface ServiceClient {
  @GET("exec")
    Call<Response> getListSoal(@Query("action") String action,
                               @Query("sheetName") String sheetName);

}

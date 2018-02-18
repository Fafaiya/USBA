package com.sandec.wakhyudi.usba.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wakhyudi on 17/02/18.
 */

public class ServiceGenerator {

    //jangan lupa ini diganti
    private static final String BASE_URL = "https://script.google.com/macros/s/AKfycbyldop574S09ZwAsivetQ9-sYSGHhvAx06rZtsHD9eZtFDE-8cr/";

    private static Gson gson = new GsonBuilder().setLenient().create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory
                    (GsonConverterFactory.create(gson))
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
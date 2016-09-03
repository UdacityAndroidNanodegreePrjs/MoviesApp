package com.example.beetel.moviesapp.data.api;


import com.facebook.stetho.okhttp.StethoInterceptor;
import com.example.beetel.moviesapp.util.MovieApiUtility;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by beetel on 12/03/2016.
 */
public class RServiceGenerator {


    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(MovieApiUtility.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static <C> C createService(Class<C> serviceClass) {
        httpClient.networkInterceptors().add(new StethoInterceptor());

        Retrofit retrofit = builder
                .client(httpClient)
                .build();
        return retrofit.create(serviceClass);
    }

}

package com.example.pinayflix.di;


import androidx.annotation.Nullable;

import com.example.pinayflix.network.MovieService;
import com.example.pinayflix.network.TVShowService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = "1a9be3fd53844f172e7fb74a75a6c5fd";

    @Singleton
    @Provides
    public MovieService provideMovieService(OkHttpClient client) {

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava3CallAdapterFactory.create())
                .build().create(MovieService.class);

    }
    @Singleton
    @Provides
    public TVShowService provideTvShowService(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava3CallAdapterFactory.create())
                .build().create(TVShowService.class);

    }

    @Singleton
    @Provides
    public OkHttpClient providesOkHTTP(){
        return new OkHttpClient.Builder().addInterceptor(chain -> {
                    Request.Builder request = chain.request().newBuilder();
                    HttpUrl originalHttp = chain.request().url();
                    HttpUrl newUrl = originalHttp.newBuilder().addQueryParameter("api_key", API_KEY).build();
                    request.url(newUrl);
                    try {
                        return chain.proceed(request.build());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .build();
    }

}

package com.example.android.sofatime.Network;

import android.content.Context;

import com.example.android.sofatime.BuildConfig;
import com.example.android.sofatime.Model.MovieReviewList;
import com.example.android.sofatime.Model.MovieTrailerList;
import com.example.android.sofatime.Model.Movies;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL ="http://api.themoviedb.org/3/";
    private static final String API_KEY = BuildConfig.movieDB_Api_Key;  //TODO(1) Insert Movie Database API Key here

    public static Retrofit getRetrofitInstance(Context context) {

        //Debug Stuff
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);



        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

    public interface GetPopularMoviesService {

        @GET("movie/popular?api_key="+ API_KEY)
        Call<Movies> getPopularMovies();
    }

    public interface GetTopRatedMoviesService {

        @GET("movie/top_rated?api_key="+ API_KEY)
        Call<Movies> getTopRatedMovies();
    }

    public interface GetSpecificTrailersService{
        @GET("movie/{id}/videos?api_key=" + API_KEY)
        Call<MovieTrailerList> getSpecificTrailers(@Path("id") int id);
    }

    public interface GetSpecificReviewsService{
        @GET("movie/{id}/reviews?api_key=" + API_KEY)
        Call<MovieReviewList> getSpecificReviews(@Path("id") int id);
    }
    


}



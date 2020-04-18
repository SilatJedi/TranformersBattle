package com.silatsaktistudios.tranformersbattle.api;


import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private final static String BASE_URL = "https://transformers-api.firebaseapp.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                    GsonConverterFactory.create(
                            new GsonBuilder().setLenient().create()
                    )
            );
    private static Retrofit retrofit = builder.build();

    private static <S> S createService(Class<S> serviceClass, final String token) {
        if (token != null) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor(chain -> {
                Request og = chain.request();
                Request request = og.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .header("Content-Type", "application/json")
                        .build();

                return chain.proceed(request);
            });

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    public static AllsparkService allsparkService = createService(AllsparkService.class, null);

    public static TransformerService transformerService(@NonNull String token) {
        return createService(TransformerService.class, token);
    }




    //    private void call2(@NonNull String token) {
//        Api.transformerService(token).getTransformers().enqueue(new Callback<TransformersResponse>() {
//
//            @Override
//            public void onResponse(@NonNull Call<TransformersResponse> call, @NonNull Response<TransformersResponse> response) {
//                Toast.makeText(AllsparkActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<TransformersResponse> call, @NonNull Throwable t) {
//                Toast.makeText(AllsparkActivity.this, "Call Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}

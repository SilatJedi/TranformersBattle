package com.silatsaktistudios.tranformersbattle.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Allspark Service. Transformers Battle API Auth Service
 */
public interface AllsparkService {

    @GET("/allspark")
    Call<String> getAllspark();
}

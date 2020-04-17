package com.silatsaktistudios.tranformersbattle.api;

import com.silatsaktistudios.tranformersbattle.model.Transformer;
import com.silatsaktistudios.tranformersbattle.model.TransformerRequest;
import com.silatsaktistudios.tranformersbattle.model.TransformersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Transformer Service. Transformer CRUD
 */
public interface TransformerService {

    String base = "/transformers";
    String baseWithId = base + "/{id}";

    /**
     * Gets a list of the Transformers created using the POST API.
     * Returns a maximum list of 50 Transformers starting from the
     * oldest created Transformer.
     */
    @GET(base)
    Call<TransformersResponse> getTransformers();

    /**
     * Creates a Transformer with the provided data in the request
     * body (in JSON). Note that the “overall rating” is not returned.
     */
    @POST(base)
    void createTransformer(@Body TransformerRequest request);

    /**
     * Updates an existing Transformer with the provided data in the
     * request body, the Transformer ID must be valid.
     */
    @PUT(base)
    void updateTransformer(@Body TransformerRequest request);

    /**
     * Gets a Transformer based on a valid ID.
     */
    @GET(baseWithId)
    Call<Transformer> getTransformer(@Path("id") int id);

    /**
     * Deletes a Transformer based on the transformer ID passed in.
     */
    @DELETE(baseWithId)
    Call<List<Transformer>> deleteTransformer(@Path("id") int id);
}

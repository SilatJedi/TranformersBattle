package com.silatsaktistudios.tranformersbattle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.silatsaktistudios.tranformersbattle.api.Api;

import io.reactivex.rxjava3.subjects.ReplaySubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllsparkViewModel extends ViewModel {

    public AllsparkViewModel() {}

    ReplaySubject<SparkState> sparkState = ReplaySubject.create();
    ReplaySubject<String> token = ReplaySubject.create();

    void getTheAllSpark() {
        sparkState.onNext(SparkState.GETTING_SPARK);

        Api.allsparkService.getAllspark().enqueue(
                new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                token.onNext(response.body());
                            } else {
                                sparkState.onNext(SparkState.FAILED_TO_GET_SPARK);
                            }
                        } else {
                            sparkState.onNext(SparkState.FAILED_TO_GET_SPARK);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        sparkState.onNext(SparkState.FAILED_TO_GET_SPARK);
                    }
                }
        );
    }

    enum SparkState {
        GETTING_SPARK,
        FAILED_TO_GET_SPARK,
        SPARK_OBTAINED
    }
}

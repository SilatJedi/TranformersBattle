package com.silatsaktistudios.tranformersbattle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.silatsaktistudios.tranformersbattle.api.Api;
import com.silatsaktistudios.tranformersbattle.model.TransformersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        call();
    }

    private void call() {
        Api.allsparkService.getAllspark().enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        call2(response.body());
                    } else {
                        Toast.makeText(MainActivity.this, "Call Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Call Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void call2(@NonNull String token) {
        Api.transformerService(token).getTransformers().enqueue(new Callback<TransformersResponse>() {

            @Override
            public void onResponse(@NonNull Call<TransformersResponse> call, @NonNull Response<TransformersResponse> response) {
                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<TransformersResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

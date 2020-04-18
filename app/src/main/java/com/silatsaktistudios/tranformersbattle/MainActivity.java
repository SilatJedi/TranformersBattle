package com.silatsaktistudios.tranformersbattle;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

public class MainActivity extends FragmentActivity {

    private FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainerView = findViewById(R.id.nav_host_fragment);
    }
}

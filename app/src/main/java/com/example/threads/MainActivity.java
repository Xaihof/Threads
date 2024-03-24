package com.example.threads;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threads.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonUIThread.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, UIThreadActivity.class));
        });

        binding.buttonAsyncTask.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, AsyncTaskActivity.class));

        });

        binding.buttonLooperThread.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, LooperThreadActivity.class));

        });

        binding.buttonCustomHandlerThread.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, CustomHandlerThreadActivity.class));

        });

    }


}
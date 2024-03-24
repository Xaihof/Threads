package com.example.threads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.threads.databinding.ActivityCustomHandlerThreadBinding;

public class CustomHandlerThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCustomHandlerThreadBinding binding;
    private static final String TAG = CustomHandlerThread.class.getSimpleName();
    private boolean mStopLoop;
    LooperThread looperThread;
    CustomHandlerThread customHandlerThread;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCustomHandlerThreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i(TAG, "Thread id of Main Thread:" + Thread.currentThread().getId());
        binding.buttonThreadStarter.setOnClickListener(this);
        binding.buttonThreadStoper.setOnClickListener(this);
        customHandlerThread = new CustomHandlerThread("CustomHandlerThread");
        customHandlerThread.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonThreadStarter) {
            mStopLoop = true;
            executeOnCustomLooperWithCustomHandler();
        } else if (v.getId() == R.id.buttonThreadStoper) {
            mStopLoop = false;
        }
    }

    public void executeOnCustomLooperWithCustomHandler() {
        customHandlerThread.mHandler.post(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop) {
                    try {
                        Thread.sleep(1000);
                        count++;
                        Log.i(TAG, "Thread id of Runnable posted: " + Thread.currentThread().getId());
                        runOnUiThread(() -> {
                            Log.i(TAG, "Thread id of runOnUiThread: " + Thread.currentThread().getId() + ", Count : " + count);
                            binding.textViewThreadCount.setText("" + count);
                        });
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Thread for interrupted");
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customHandlerThread != null) {
            customHandlerThread.getLooper().quit();
        }
    }
}
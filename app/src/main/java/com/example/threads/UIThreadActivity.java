package com.example.threads;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.threads.databinding.ActivityUithreadBinding;

public class UIThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityUithreadBinding binding;

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mStopLoop;
    int count = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUithreadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize handler with reference to looper.
        // this will make sure that the handler have a reference to the MainLooper/UI Thread/Main Thread.
        // this line is commented because we have used an other alternative(shortcut) to achieve the same functionality.
        /*handler = new Handler(getApplicationContext().getMainLooper());*/
        Log.i(TAG, "Thread id: " + Thread.currentThread().getId());

        binding.buttonThreadStarter.setOnClickListener(this);
        binding.buttonThreadStoper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonThreadStarter) {
            mStopLoop = true;

            // This loop is running in a separate Thread. (Otherwise it will takeover all the thread and the stop button won't work)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mStopLoop) {
                        try {
                            Thread.sleep(1000);
                            count++;
                        } catch (InterruptedException e) {
                            Log.i(TAG, e.getMessage());
                        }
                        Log.i(TAG, "Thread id in while loop: " + Thread.currentThread().getId() + ", Count" + count);

                        // this line is commented because we have used an other alternative(shortcut) to achieve the same functionality.
                        /*handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewThreadCount.setText(""+count);
                            }
                        });*/

                        // this is the alternative/shortcut to achieve same functionality.
                        binding.textViewThreadCount.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewThreadCount.setText("" + count);
                            }
                        });
                    }
                }
            }).start();

        } else if (v.getId() == R.id.buttonThreadStoper) {
            mStopLoop = false;
        }
    }
}
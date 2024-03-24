package com.example.threads;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threads.databinding.ActivityAsyncTaskBinding;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAsyncTaskBinding binding;

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mStopLoop;
    int count = 0;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAsyncTaskBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        Log.i(TAG, "Thread id: " + Thread.currentThread().getId());

        binding.buttonThreadStarter.setOnClickListener(this);
        binding.buttonThreadStoper.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonThreadStarter) {
            mStopLoop = true;
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(0);
        } else if (v.getId() == R.id.buttonThreadStoper) {
            myAsyncTask.cancel(true);
        }


    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        private int customCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            customCounter = integers[0];
            while (mStopLoop) {
                try {
                    Thread.sleep(1000);
                    customCounter++;
                    publishProgress(customCounter);
                } catch (InterruptedException e) {
                    Log.i(TAG, e.getMessage());
                }
                if (isCancelled()) {
                    break;
                }
            }
            return customCounter;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            binding.textViewThreadCount.setText("" + values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            binding.textViewThreadCount.setText("" + integer);
            count = integer;
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
        }
    }
}
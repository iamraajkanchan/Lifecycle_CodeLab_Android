/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.lifecycles.step3;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import com.example.android.codelabs.lifecycle.R;


public class ChronoActivity3 extends AppCompatActivity {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chrono_activity_3);
        mLiveDataTimerViewModel = new ViewModelProvider(this).get(LiveDataTimerViewModel.class);
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onCreate :: " + this.getLifecycle().getCurrentState());
        subscribe();
    }

    private void subscribe() {
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = ChronoActivity3.this.getResources().getString(
                        R.string.seconds, aLong);
                ((TextView) findViewById(R.id.timer_textview)).setText(newText);
                Log.d("ChronoActivity3", "Updating timer");
            }
        };
        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onStart :: " + this.getLifecycle().getCurrentState());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onRestart :: " + this.getLifecycle().getCurrentState());
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onRestart :: " + this.getLifecycle().getCurrentState());
    }

    @Override
    protected void onStop() {
        super.onStop();
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onRestart :: " + this.getLifecycle().getCurrentState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /* Use this method to get the current state of the Activity. */
        Log.d("ChronoActivity_LiveData", "onRestart :: " + this.getLifecycle().getCurrentState());
    }
}

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

package com.example.android.lifecycles.step4;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * LifecycleOwner is used to bind {@link LocationActivity} with BoundLocationManager.
 * LocationListener is used to communicate with {@link LocationActivity}.
 * Context is used to get an instance of LocationManager in BoundLocationManager class.
 * */
public class BoundLocationManager {
    public static void bindLocationListenerIn(LifecycleOwner lifecycleOwner,
                                              LocationListener listener, Context context) {
        new BoundLocationListener(lifecycleOwner, listener, context);
    }

    @SuppressWarnings("MissingPermission")
    static class BoundLocationListener implements LifecycleObserver {
        private final Context mContext;
        private LocationManager mLocationManager;
        private final LocationListener mListener;

        public BoundLocationListener(LifecycleOwner lifecycleOwner,
                                     LocationListener listener, Context context) {
            mContext = context;
            mListener = listener;
            lifecycleOwner.getLifecycle().addObserver(this);
        }

        /**
         * Typically addLocationListener should be called in the onResume callback method of the Activity.
         *
         * Annotation that can be used to mark methods on {@link LifecycleObserver} implementations that
         * should be invoked to handle lifecycle events.
         *
         * @deprecated This annotation required the usage of code generation or reflection, which should
         * be avoided. Use {@link DefaultLifecycleObserver} or
         * {@link LifecycleEventObserver} instead.
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void addLocationListener() {
            // Note: Use the Fused Location Provider from Google Play Services instead.
            // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi

            mLocationManager =
                    (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
            Log.d("Location_Example", "BoundLocationManager :: Listener added");

            // Force an update with the last location, if available.
            Location lastLocation = mLocationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                mListener.onLocationChanged(lastLocation);
            }
        }

        /**
         * Typically removeLocationListener should be called in the onPause callback method of the Activity.
         * Annotation that can be used to mark methods on {@link LifecycleObserver} implementations that
         * should be invoked to handle lifecycle events.
         *
         * @deprecated This annotation required the usage of code generation or reflection, which should
         * be avoided. Use {@link DefaultLifecycleObserver} or
         * {@link LifecycleEventObserver} instead.
         */
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void removeLocationListener() {
            if (mLocationManager == null) {
                return;
            }
            mLocationManager.removeUpdates(mListener);
            mLocationManager = null;
            Log.d("Location_Example", "BoundLocationManager :: Listener removed");
        }
    }
}

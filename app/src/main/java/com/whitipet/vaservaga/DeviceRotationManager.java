package com.whitipet.vaservaga;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

final class DeviceRotationManager implements SensorEventListener {

	private final Context context;
	private final OnRotationChangedListener onRotationChangedListener;

	DeviceRotationManager(Context context, OnRotationChangedListener onRotationChangedListener) {
		this.context = context;
		this.onRotationChangedListener = onRotationChangedListener;
	}

	void onStart() {
		registerSensorsListener();
	}

	void onStop() {
		unregisterSensorsListener();
	}

	private SensorManager sensorManager;

	private SensorManager getSensorManager() {
		if (sensorManager == null) {
			sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
		}
		return sensorManager;
	}

	private Sensor accelerometer;

	private Sensor getAccelerometer() {
		if (accelerometer == null) {
			accelerometer = getSensorManager().getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		}
		return accelerometer;
	}

	private void registerSensorsListener() {
		boolean registered = false;
		SensorManager sensorManager = getSensorManager();
		if (sensorManager != null) {
			Sensor accelerometer = getAccelerometer();
			if (accelerometer != null) {
				sensorManager.registerListener(this, accelerometer,
						SensorManager.SENSOR_DELAY_FASTEST, SensorManager.SENSOR_DELAY_UI
				);
				registered = true;
			}
		}
		if (!registered) {
			Toast.makeText(context, "Sensor unavailable", Toast.LENGTH_SHORT).show();
		}
	}

	private void unregisterSensorsListener() {
		SensorManager sensorManager = getSensorManager();
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO:
		// onRotationChangedListener.onRotationChanged(tilt, pitch);
	}

	interface OnRotationChangedListener {

		void onRotationChanged(float tilt, float pitch);
	}
}
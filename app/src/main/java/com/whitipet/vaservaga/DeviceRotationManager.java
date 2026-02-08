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

	private SensorManager sensorManager;

	void onStart() {
		SensorManager manager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
		if (manager == null) return;

		Sensor gravitySensor = manager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		if (gravitySensor != null) {
			manager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
			sensorManager = manager;
		} else {
			Toast.makeText(context, "Sensor unavailable", Toast.LENGTH_SHORT).show();
		}
	}

	void onStop() {
		if (sensorManager == null) return;
		sensorManager.unregisterListener(this);
		sensorManager = null;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_GRAVITY) return;
		onRotationChangedListener.onRotationChanged(event.values[0] / 10.0f, -event.values[1] / 10.0f);
	}

	interface OnRotationChangedListener {
		void onRotationChanged(float x, float y);
	}
}
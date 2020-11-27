package com.whitipet.vaservaga;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

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

	private Sensor rotationVectorSensor;

	private Sensor getRotationVectorSensor() {
		if (rotationVectorSensor == null) {
			rotationVectorSensor = getSensorManager().getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		}
		return rotationVectorSensor;
	}

	private void registerSensorsListener() {
		boolean registered = false;
		SensorManager sensorManager = getSensorManager();
		if (sensorManager != null) {
			Sensor rotationVectorSensor = getRotationVectorSensor();
			if (rotationVectorSensor != null) {
				sensorManager.registerListener(this, rotationVectorSensor,
						SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_DELAY_UI
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

	private final float[] rotationMatrix = new float[9];
	private final float[] orientation = new float[3];

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ROTATION_VECTOR) {
			return;
		}

		SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
		// SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrix);
		SensorManager.getOrientation(rotationMatrix, orientation);

		float pitch = (float) Math.toDegrees(orientation[1]);
		float roll = (float) Math.toDegrees(orientation[2]);
		if (roll > 90) {
			roll = 90 - (roll - 90);
		} else if (roll < -90) {
			roll = -(90 + (roll + 90));
		}
		onRotationChangedListener.onRotationChanged(pitch, roll);

		Log.d("DeviceRotationManager", df.format(pitch) + "       " + df.format(roll));
	}

	private final static DecimalFormat df = new DecimalFormat() {{
		setMinimumIntegerDigits(3);
		setMaximumIntegerDigits(3);
		setMinimumFractionDigits(3);
		setMaximumFractionDigits(3);
		setPositivePrefix(" ");
		setNegativePrefix("-");
	}};

	interface OnRotationChangedListener {

		void onRotationChanged(float pitch, float roll);
	}
}
package com.whitipet.vaservaga;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private LevelView levelView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		levelView = new LevelView(this);
		setContentView(levelView);

		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (sensorManager != null) {
			Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
			if (gravitySensor != null) {
				sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
			levelView.setData(event.values[0] * 0.1f, -event.values[1] * 0.1f);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
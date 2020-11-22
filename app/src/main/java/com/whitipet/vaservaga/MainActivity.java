package com.whitipet.vaservaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private DeviceRotationManager deviceRotationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LevelView levelView = new LevelView(this);
		setContentView(levelView);

		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

		deviceRotationManager = new DeviceRotationManager(this, levelView::setData);
	}

	@Override
	protected void onStart() {
		super.onStart();
		deviceRotationManager.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		deviceRotationManager.onStop();
	}
}
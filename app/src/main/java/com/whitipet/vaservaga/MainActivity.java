package com.whitipet.vaservaga;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new LevelView(this));

		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
	}
}
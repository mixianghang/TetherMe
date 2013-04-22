package com.app.widget.tetherme;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity{

	TextView main_label,intro;
	Typeface tf_bold,tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tf=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Condensed.ttf");
		tf_bold=Typeface.createFromAsset(getAssets(), "fonts/Roboto-BoldCondensed.ttf");
		
		main_label=(TextView) findViewById(R.id.main_label);
		intro=(TextView) findViewById(R.id.textView_intro);
		
		main_label.setTypeface(tf_bold);
		intro.setTypeface(tf);
	}
}

package com.app.widget.tetherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class FlightModeToggle extends BroadcastReceiver{

	Context c;
	IsFeatureEnabled is_feature;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
	
		c=arg0;
		
		is_feature=new IsFeatureEnabled(c);
		
		if(is_feature.isFlightModeOn())
		{
			Settings.System.putInt(c.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0);
			Toast.makeText(c, "Flight Mode Turned Off", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Settings.System.putInt(c.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 1);
			Toast.makeText(c, "Flight Mode Turned On", Toast.LENGTH_SHORT).show();
		}
		
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", is_feature.isFlightModeOn());
		c.sendBroadcast(intent);
		
		Intent towidgetUpdate=new Intent(c,widgetUpdateService.class);
		c.startService(towidgetUpdate);
	}
}

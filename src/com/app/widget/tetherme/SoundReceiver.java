package com.app.widget.tetherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SoundReceiver extends BroadcastReceiver{

	Context c;
	
	public void onReceive(Context arg0, Intent arg1) {
		c=arg0;
		Intent towidgetUpdate=new Intent(c,widgetUpdateService.class);
		c.startService(towidgetUpdate);
	}
}

package com.app.widget.tetherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HotspotReceiver extends BroadcastReceiver{

	Context c;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		c=arg0;
		Intent WidgetService=new Intent(c.getApplicationContext(),widgetUpdateService.class);
		c.startService(WidgetService);
	}	
}

package com.app.widget.tetherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class SwitchOnWifi extends BroadcastReceiver{

	IsFeatureEnabled is_feature;
	Context c;
	WifiManager wifi;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		 c=arg0;
		 is_feature=new IsFeatureEnabled(c);
		 wifi=(WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		 
		 if(!is_feature.iswifienabled())
		 {
			 TurnOnWifi();
			 Intent toConnect=new Intent(c,ConnectToWifi.class);
			 toConnect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 c.startActivity(toConnect);
		 }
		 else
			 TurnOffWifi();
		 
		 Intent WidgetService=new Intent(c.getApplicationContext(),widgetUpdateService.class);
		 c.startService(WidgetService);
	}
	
	public void TurnOnWifi()
	{
		wifi.setWifiEnabled(true);
	}
	
	public void TurnOffWifi()
	{
		wifi.setWifiEnabled(false);
	}

}

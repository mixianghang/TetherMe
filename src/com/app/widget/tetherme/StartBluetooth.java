package com.app.widget.tetherme;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartBluetooth extends BroadcastReceiver {

	Context c;
	IsFeatureEnabled is_feature;
	BluetoothAdapter bluetooth;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		c=arg0;
		bluetooth=BluetoothAdapter.getDefaultAdapter();
		is_feature=new IsFeatureEnabled(c);
		
		if(is_feature.isbluetoothenabled())
		{
			bluetooth.disable();
			Toast.makeText(c, "Switching Off Bluetooth", Toast.LENGTH_SHORT).show();
		}
		else
		{
			bluetooth.enable();
			Toast.makeText(c, "Switching On Bluetooth", Toast.LENGTH_SHORT).show();
		}
		Intent towidgetUpdate=new Intent(c,widgetUpdateService.class);
		c.startService(towidgetUpdate);
		
	}

}

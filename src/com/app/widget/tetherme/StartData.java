package com.app.widget.tetherme;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class StartData extends BroadcastReceiver{

	Context c;
	IsFeatureEnabled is_feature;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		c=arg0;
		is_feature=new IsFeatureEnabled(c);
		
		if(is_feature.isMobileDataEnabled())
			setMobileDataEnabled(c, false);
		else
			setMobileDataEnabled(c, true);
		
		Intent WidgetService=new Intent(c,widgetUpdateService.class);
		c.startService(WidgetService);
	}
	
	@SuppressWarnings({ })
	private void setMobileDataEnabled(Context context, boolean enabled) {
		try
		{
			
			final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final Class<?> conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField.get(conman);
			final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
			
			if(enabled)
				Toast.makeText(c, "Switching on Data", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(c, "Switching off Data", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			
		}
	}

}

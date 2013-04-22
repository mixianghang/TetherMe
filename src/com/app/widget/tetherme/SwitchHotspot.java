package com.app.widget.tetherme;

import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;


public class SwitchHotspot extends BroadcastReceiver{

	String name="",password="";
	SharedPreferences hotspot;
	IsFeatureEnabled is_feature;
	Context c;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		c=arg0;
		
		hotspot= c.getSharedPreferences("hotspot",Context.MODE_PRIVATE);
		is_feature=new IsFeatureEnabled(c);
		
		if(hotspot.getBoolean("config_complete", false))
		{
			name=hotspot.getString("hotspot_name", "TetherAP");
			password=hotspot.getString("hotspot_password", "password");
			if(is_feature.isHotspotOn())
				switchOnHotspot(false);
			else
				switchOnHotspot(true);
			
			
		}
		else
		{
			Intent toStartHotspot=new Intent(c,StartHotspot.class);
			toStartHotspot.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(toStartHotspot);
		}
		
		Intent WidgetService=new Intent(c,widgetUpdateService.class);
		c.startService(WidgetService);
		
	}
	
	public void switchOnHotspot(boolean key)
    {
	
		WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        
        for(Method method: wmMethods){
            
            if(method.getName().equals("setWifiApEnabled")) {
                WifiConfiguration netConfig = new WifiConfiguration();
                netConfig.SSID = name;
                netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                netConfig.preSharedKey=password;
                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                try {
                    
                    method.invoke(wifi, netConfig, key);
                   // if (netConfig.wepKeys!=null && netConfig.wepKeys.length>=1)
                    //mIsWifiEnabled = true;
                } catch (Exception e) {
                   
                }
            }
        }
        
        if(key)
        	Toast.makeText(c, "Tethering or Hotspot Active", Toast.LENGTH_SHORT).show();
    }
	
}

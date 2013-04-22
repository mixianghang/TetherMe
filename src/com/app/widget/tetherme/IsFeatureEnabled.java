package com.app.widget.tetherme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;


public class IsFeatureEnabled {

	Context context;
	
	public IsFeatureEnabled(Context c)
	{
		context=c;
	}
	
	public Boolean isTetheringOn()
	{
		String[] available = new String[]{};
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method[] wmMethods = cm.getClass().getDeclaredMethods();

	    for(Method method: wmMethods){
	      if(method.getName().equals("getTetherableIfaces")){
	        try {
	        	available = (String[]) method.invoke(cm);
	            break;
	        } catch (IllegalArgumentException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        } catch (IllegalAccessException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        } catch (InvocationTargetException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        }
	      }
	    }
		
		if(available.length==0)
			return true;
		else
			return false;
	}

	public Boolean isTetheredOn()
	{
		String[] available = new String[]{};
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		Method[] wmMethods = cm.getClass().getDeclaredMethods();

	    for(Method method: wmMethods){
	    	
	    	if(method.getName().equals("getTetheredIfaces")){
	        try {
	        	 available = (String[]) method.invoke(cm);
	             break;
	        } catch (IllegalArgumentException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        } catch (IllegalAccessException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        } catch (InvocationTargetException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            
	        }
	      }
	    }
	    
	    
	   if(available.length==0)
			return false;
		else
			return true;
	}

	public Boolean isHotspotOn()
	{
		boolean isWifiAPenabled = false;
		WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		Method[] wmMethods = wifi.getClass().getDeclaredMethods();
		for(Method method: wmMethods){
		
			if(method.getName().equals("isWifiApEnabled")) {

				try {
					isWifiAPenabled= (Boolean) method.invoke(wifi);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
	
			}
		}
		return isWifiAPenabled;
	}
	
	public Boolean isMobileDataEnabled()
	{
		boolean mobileDataEnabled = false; // Assume disabled
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    try {
	        
			Class<?> cmClass = Class.forName(cm.getClass().getName());
	        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
	        method.setAccessible(true); // Make the method callable
	        // get the setting for "mobile data"
	        mobileDataEnabled = (Boolean)method.invoke(cm);
	        
	    } catch (Exception e) {
	       
	    }
	    
	    return mobileDataEnabled;
	}

	public Boolean iswifienabled()
	{
		WifiManager wifi=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		return wifi.isWifiEnabled();
	}

	public Boolean isbluetoothenabled()
	{
		BluetoothAdapter bluetooth=BluetoothAdapter.getDefaultAdapter();
		return bluetooth.isEnabled();
	}
	
	public Boolean isFlightModeOn()
	{
		Boolean isEnabled=false;
		
		isEnabled=Settings.System.getInt(
			      context.getContentResolver(), 
			      Settings.System.AIRPLANE_MODE_ON, 0) == 1;
		
		return isEnabled;
	}
	
	public Boolean isSoundOn()
	{
		Boolean isEnabled=true;
		
		AudioManager audioman=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		switch(audioman.getRingerMode())
		{
		case AudioManager.RINGER_MODE_SILENT:
	        isEnabled=false;
	        break;
	    case AudioManager.RINGER_MODE_VIBRATE:
	        isEnabled=false;
	        break;
	    case AudioManager.RINGER_MODE_NORMAL:
	        isEnabled=true;
	        break;
		}
		
		return isEnabled;
	}
	
}


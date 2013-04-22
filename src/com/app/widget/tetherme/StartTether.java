package com.app.widget.tetherme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartTether extends BroadcastReceiver{

	IsFeatureEnabled is_feature;
	Context c;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		c=arg0;
		is_feature=new IsFeatureEnabled(c.getApplicationContext()); 
		
		if(is_feature.isTetheringOn())
			switchOffTethering();
		else
			switchOnTethering();
		
		
		
		Intent WidgetService=new Intent(c.getApplicationContext(),widgetUpdateService.class);
		c.startService(WidgetService);
	}
	
	public void switchOnTethering() {
		
        Object obj = c.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        for (Method m : obj.getClass().getDeclaredMethods()) {
        	
            if (m.getName().equals("tether")) {
            	
            	try {
                    m.invoke(obj, "usb0"); 
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c.getApplicationContext(), "Connect Usb", Toast.LENGTH_SHORT).show();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c.getApplicationContext(), "Connect Usb", Toast.LENGTH_SHORT).show();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c.getApplicationContext(), "Connect Usb", Toast.LENGTH_SHORT).show();
                }
            }
          
        }
        
        Toast.makeText(c, "Tethering or Hotspot Active", Toast.LENGTH_SHORT).show();
}
	
	public void switchOffTethering()
	{
		
        Object obj = c.getSystemService(Context.CONNECTIVITY_SERVICE);
        for (Method m : obj.getClass().getDeclaredMethods()) {
        	
            if (m.getName().equals("untether")) {
            	
                try {
                    m.invoke(obj, "usb0");
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c, "Connect Usb", Toast.LENGTH_SHORT).show();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c, "Connect Usb", Toast.LENGTH_SHORT).show();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(c, "Connect Usb", Toast.LENGTH_SHORT).show();
                }
            }
        }
	}
	
}

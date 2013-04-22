package com.app.widget.tetherme;

import java.lang.reflect.Method;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;


public class StartHotspot extends Activity implements OnClickListener{

	EditText hotspot_name,hotspot_password;
	TextView at_connects_title,hotspot_nam,hotspot_pass;
	TextView create_hotspot;
	SharedPreferences hotspot;
	SharedPreferences.Editor hotspot_edit;
	String name="",password="";
	Typeface tf,tf_bold;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotspot);
		
		tf=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		tf_bold=Typeface.createFromAsset(getAssets(), "fonts/Roboto-BoldCondensed.ttf");
		
		hotspot=getSharedPreferences("hotspot",MODE_PRIVATE);
		hotspot_edit=hotspot.edit();
		
		at_connects_title=(TextView) findViewById(R.id.atconnects_label);
		hotspot_nam=(TextView) findViewById(R.id.textView_hotspotname);
		hotspot_pass=(TextView) findViewById(R.id.textView_password);
		hotspot_name=(EditText) findViewById(R.id.editText_hotspotname);
		hotspot_password=(EditText) findViewById(R.id.editText_password);
		create_hotspot=(TextView) findViewById(R.id.button_create);
		
		at_connects_title.setTypeface(tf_bold);
		hotspot_nam.setTypeface(tf_bold);
		hotspot_pass.setTypeface(tf_bold);
		hotspot_name.setTypeface(tf);
		hotspot_password.setTypeface(tf);
		create_hotspot.setTypeface(tf_bold);
		
		hotspot_name.setText(hotspot.getString("hotspot_name", ""));
		hotspot_password.setText(hotspot.getString("hotspot_password",""));
		
		create_hotspot.setOnClickListener(this);
		
	}

	public void onClick(View arg0) {
		
		name=hotspot_name.getText().toString().trim();
		password=hotspot_password.getText().toString();
		switchOnHotspot();
		hotspot_edit.putString("hotspot_name", name);
		hotspot_edit.putString("hotspot_password", password);
		hotspot_edit.putBoolean("config_complete", true);
		hotspot_edit.commit();
		finish();
	}

	public void switchOnHotspot()
    {
		
    	WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
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
                    
                    method.invoke(wifi, netConfig, true);
                   // if (netConfig.wepKeys!=null && netConfig.wepKeys.length>=1)
                    //mIsWifiEnabled = true;
                } catch (Exception e) {
                   
                }
            }
        }
    }

}

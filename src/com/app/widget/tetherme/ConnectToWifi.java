package com.app.widget.tetherme;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class ConnectToWifi extends Activity implements OnItemClickListener{

	List<ScanResult> Wifiresults;
	WifiManager wifi;
	TextView progress_text,ssid,wifi_label,at_connects_label,ssid_label,security,password_label;
	EditText password;
	TextView connect;
	ProgressBar progress;
	ListView wifi_list;
	ArrayAdapter<String> adapter;
	int i=0,flag=0;
	String[] available_networks;
	String wifi_ssid_name="";
	BroadcastReceiver wifi_receiver;
	WifiConfiguration config;
	
	Typeface tf,tf_bold;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifinetwork);
		
		tf=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		tf_bold=Typeface.createFromAsset(getAssets(), "fonts/Roboto-BoldCondensed.ttf");
		
		wifi=(WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifi.setWifiEnabled(true);
		
		progress_text=(TextView) findViewById(R.id.textView_progress);
		wifi_label=(TextView) findViewById(R.id.wifi_label);
		progress=(ProgressBar) findViewById(R.id.progress);
		wifi_list=(ListView) findViewById(R.id.listView_wifi);
		
		progress_text.setTypeface(tf_bold);
		wifi_label.setTypeface(tf_bold);
		
		ScanForNetworks();
		
		config=new WifiConfiguration();
		
		wifi_list.setOnItemClickListener(this);
		
		}

	public void ScanForNetworks()
	{

		wifi.startScan();
		
		wifi_receiver=new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context c, Intent intent) 
			{
				try
				{
					Wifiresults = wifi.getScanResults();
					available_networks=new String[Wifiresults.size()];
        	
					for(ScanResult s:Wifiresults)
					{
						available_networks[i]=s.SSID;
						i++;
					}
        	
					if(Wifiresults.size()!=0)
					{
						adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item,available_networks);
					
						wifi_list.setVisibility(View.VISIBLE);
						wifi_list.setAdapter(adapter);
        	
						progress.setVisibility(View.GONE);
						progress_text.setVisibility(View.GONE);
					}
					else
					{
						progress.setVisibility(View.GONE);
						progress_text.setText("No Networks Found Within Range");
					}
					
					unregisterReceiver(this);
					
					}catch(Exception e){
					
					}
				
			}
		
		};
		
		registerReceiver(wifi_receiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));       

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
		WifiConfiguration already_configured=new WifiConfiguration();
		List<WifiConfiguration> list = wifi.getConfiguredNetworks();
		for( WifiConfiguration i : list ) {
		    if(i.SSID != null && i.SSID.equals("\"" + available_networks[arg2] + "\"")) {
		    	flag=1;
		    	already_configured=i;
		    }
		    else 
		    	flag=0;
		}
		
		if(flag==1)
		{
			wifi.disconnect();
	    	wifi.enableNetwork(already_configured.networkId, true);
	    	wifi.reconnect();
	    	
	    	Toast.makeText(getApplicationContext(), "Connected to"+available_networks[arg2], Toast.LENGTH_SHORT).show();
	    	
	    	finish();
	    }
		else
		{
			Dialog d=new Dialog(this,Window.FEATURE_NO_TITLE);
			d.setCancelable(true);
			d.setContentView(R.layout.wifidialog);
			d.show();
			
			at_connects_label=(TextView)d.findViewById(R.id.atconnects_label);
			ssid_label=(TextView)d.findViewById(R.id.textView_SSID);
			ssid=(TextView)d.findViewById(R.id.textView_SSID_Name);
			password_label=(TextView)d.findViewById(R.id.textView_Password);
			security=(TextView)d.findViewById(R.id.textView_Security);
			password=(EditText)d.findViewById(R.id.editText_ssid_password);
			connect=(TextView)d.findViewById(R.id.button_connect);
			
			at_connects_label.setTypeface(tf_bold);
			ssid_label.setTypeface(tf_bold);
			ssid.setTypeface(tf_bold);
			password_label.setTypeface(tf_bold);
			security.setTypeface(tf);
			password.setTypeface(tf);
			connect.setTypeface(tf_bold);
			
			wifi_ssid_name=available_networks[arg2];
			config.SSID="\"" + wifi_ssid_name + "\"";
			ssid.setText(wifi_ssid_name);
			
			connect.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					
					config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
					config.preSharedKey="\"" + password.getText().toString() +"\"";
					wifi.addNetwork(config);
					wifi.saveConfiguration();
					
					List<WifiConfiguration> list = wifi.getConfiguredNetworks();
					for( WifiConfiguration i : list ) {
					    if(i.SSID != null && i.SSID.equals("\"" + wifi_ssid_name + "\"")) {
					    	
					    	wifi.disconnect();
					    	wifi.enableNetwork(i.networkId, true);
					    	wifi.reconnect();
					    	
					    	Toast.makeText(getApplicationContext(), "Connecting to "+wifi_ssid_name, Toast.LENGTH_SHORT).show();
					    	
					    	finish();
					    }
					}
					
				}
				
				
			}
			
			);
		}
	}	
}

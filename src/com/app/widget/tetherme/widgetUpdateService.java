package com.app.widget.tetherme;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class widgetUpdateService extends Service {

	IsFeatureEnabled is_feature;
	
	@Override
	public void onStart(Intent intent, int startId) {
	
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
		ComponentName thisWidget = new ComponentName(getApplicationContext(),TetherMe.class);
		RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
  	          R.layout.widget_layout);
		
		is_feature=new IsFeatureEnabled(this);
		
		Intent i_data=new Intent(this.getApplicationContext(),StartData.class);
		PendingIntent p_data=PendingIntent.getBroadcast(this.getApplicationContext(),PendingIntent.FLAG_UPDATE_CURRENT, i_data, PendingIntent.FLAG_UPDATE_CURRENT);
		if(is_feature.isMobileDataEnabled())
			remoteViews.setImageViewResource(R.id.button_threeg, R.drawable.threegicon_on);
		else
			remoteViews.setImageViewResource(R.id.button_threeg, R.drawable.threegicon);
		remoteViews.setOnClickPendingIntent(R.id.button_threeg, p_data);
		
		Intent i_tether = new Intent(this.getApplicationContext(), StartTether.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),0, i_tether, PendingIntent.FLAG_UPDATE_CURRENT);			
		if(is_feature.isTetheredOn() & is_feature.isTetheringOn())
			remoteViews.setImageViewResource(R.id.button_tether, R.drawable.tether_on);
		else if(!is_feature.isTetheredOn() & !is_feature.isTetheringOn() & is_feature.isHotspotOn())
			remoteViews.setImageViewResource(R.id.button_tether, R.drawable.tether);
		else if(is_feature.isTetheredOn() & is_feature.isTetheringOn() & is_feature.isHotspotOn())
			remoteViews.setImageViewResource(R.id.button_tether, R.drawable.tether_on);
		else
			remoteViews.setImageViewResource(R.id.button_tether, R.drawable.tether);
		
		
		 
		 remoteViews.setOnClickPendingIntent(R.id.button_tether, pendingIntent);
		
		Intent i_hotspot=new Intent(this.getApplicationContext(),SwitchHotspot.class);
		PendingIntent pi_hotspot=PendingIntent.getBroadcast(this.getApplicationContext(), 0, i_hotspot, PendingIntent.FLAG_UPDATE_CURRENT);
		if(is_feature.isHotspotOn())
		{
			remoteViews.setImageViewResource(R.id.button_hotspot, R.drawable.hotspot_on);
		}
		else
			remoteViews.setImageViewResource(R.id.button_hotspot, R.drawable.hotspot);
		remoteViews.setOnClickPendingIntent(R.id.button_hotspot, pi_hotspot);
			
		Intent i_hotspot_config=new Intent(this.getApplicationContext(),StartHotspot.class);
		PendingIntent pi_hotspot_config=PendingIntent.getActivity(this.getApplicationContext(), 0, i_hotspot_config, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.button_settings, pi_hotspot_config);
			
		Intent i_wifi=new Intent(this.getApplicationContext(),SwitchOnWifi.class);
		PendingIntent pi_wifi=PendingIntent.getBroadcast(this.getApplicationContext(), 0, i_wifi,PendingIntent.FLAG_UPDATE_CURRENT);
		if(is_feature.iswifienabled())
			remoteViews.setImageViewResource(R.id.button_wifi, R.drawable.wifi_on);
		else
			remoteViews.setImageViewResource(R.id.button_wifi, R.drawable.wifi);
		remoteViews.setOnClickPendingIntent(R.id.button_wifi, pi_wifi);
		
		Intent i_bluetooth=new Intent(this.getApplicationContext(),StartBluetooth.class);
		PendingIntent pi_bluetooth=PendingIntent.getBroadcast(this.getApplicationContext(), 0, i_bluetooth, PendingIntent.FLAG_UPDATE_CURRENT);
		//if(is_feature.isbluetoothenabled())
			remoteViews.setImageViewResource(R.id.button_bluetooth,R.drawable.bluetooth_on);
		//else
			remoteViews.setImageViewResource(R.id.button_bluetooth,R.drawable.bluetooth);
		remoteViews.setOnClickPendingIntent(R.id.button_bluetooth, pi_bluetooth);
		
		Intent i_flightmode=new Intent(this.getApplicationContext(),FlightModeToggle.class);
		PendingIntent pi_flightmode=PendingIntent.getBroadcast(this.getApplicationContext(), 0, i_flightmode, PendingIntent.FLAG_UPDATE_CURRENT);
		if(is_feature.isFlightModeOn())
			remoteViews.setImageViewResource(R.id.button_flightmode,R.drawable.flightmode_on);
		else
			remoteViews.setImageViewResource(R.id.button_flightmode,R.drawable.flightmode);
		remoteViews.setOnClickPendingIntent(R.id.button_flightmode, pi_flightmode);
		
		Intent i_sound=new Intent(this.getApplicationContext(),SoundToggle.class);
		PendingIntent pi_sound=PendingIntent.getBroadcast(this.getApplicationContext(), 0, i_sound, PendingIntent.FLAG_UPDATE_CURRENT);
		if(is_feature.isSoundOn())
			remoteViews.setImageViewResource(R.id.button_sound, R.drawable.sound_on);
		else
			remoteViews.setImageViewResource(R.id.button_sound, R.drawable.sound_off);
		remoteViews.setOnClickPendingIntent(R.id.button_sound, pi_sound);
		
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		
		stopSelf();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
}

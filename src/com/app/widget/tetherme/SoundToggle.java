package com.app.widget.tetherme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class SoundToggle extends BroadcastReceiver{

	Context c;
	IsFeatureEnabled is_feature;
	AudioManager audioman;
	
	public void onReceive(Context arg0, Intent arg1) {
		
		c=arg0;
		is_feature=new IsFeatureEnabled(c);
		audioman=(AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
		
		if(is_feature.isSoundOn())
		{
			audioman.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			audioman.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		else
		{
			audioman.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
		
		Intent towidgetUpdate=new Intent(c,widgetUpdateService.class);
		c.startService(towidgetUpdate);
		
	}

}

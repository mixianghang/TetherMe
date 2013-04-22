package com.app.widget.tetherme;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class TetherMe extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		
		Intent intent = new Intent(context.getApplicationContext(),widgetUpdateService.class);
		context.startService(intent);
	}

}

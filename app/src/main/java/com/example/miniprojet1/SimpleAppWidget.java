package com.example.miniprojet1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SimpleAppWidget extends AppWidgetProvider {

    String location;
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // Planifiez des mises à jour périodiques lorsque le widget est activé
        schedulePeriodicUpdate(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Arrêtez les mises à jour périodiques lorsque le dernier widget est désactivé
        cancelPeriodicUpdate(context);
    }

    private void schedulePeriodicUpdate(Context context) {
        // Créez une intention pour mettre à jour le widget
        Intent intent = new Intent(context, SimpleAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Planifiez des mises à jour périodiques toutes les 60 secondes (60000 millisecondes)
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent);
    }

    private void cancelPeriodicUpdate(Context context) {
        // Créez une intention pour mettre à jour le widget
        Intent intent = new Intent(context, SimpleAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Arrêtez les mises à jour périodiques en annulant le PendingIntent
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construire l'objet RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_app);

        Bundle extras = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (extras != null) {
            String location = extras.getString("cityname");
            if (location != null) {
                views.setTextViewText(R.id.location, location);
            }
        }

        // Pour la date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String localDate = dateFormat.format(calendar.getTime());

        views.setTextViewText(R.id.date, localDate);

        // Construct an Intent object to open MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Définir l'intention en attente pour être déclenchée lorsque le widget est cliqué
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);

        // Instruire le gestionnaire de widget de mettre à jour le widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
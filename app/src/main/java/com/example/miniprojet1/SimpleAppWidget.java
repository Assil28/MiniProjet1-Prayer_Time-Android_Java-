package com.example.miniprojet1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.miniprojet1.Models.PrayerModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SimpleAppWidget extends AppWidgetProvider {
    private MediaPlayer mediaPlayer;
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
        // Libérez les ressources du lecteur multimédia
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        // Le widget a été placé sur l'écran, planifiez des mises à jour périodiques
        schedulePeriodicUpdate(context);
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
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 1000*60;
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.prayer_alarm);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construire l'objet RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_app);

       // pour recuperer la locatisation
        SharedPreferences preferences = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String cityName = preferences.getString("cityName", "Unknown");
        views.setTextViewText(R.id.location, cityName);



        // Pour la date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String localDate = dateFormat.format(calendar.getTime());

        views.setTextViewText(R.id.date, localDate);

        //Pour le  Temps
        SimpleDateFormat heuereFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String localHeure = heuereFormat.format(calendar.getTime());
        Log.d("WS","Time :"+localHeure);
            views.setTextViewText(R.id.localTime,localHeure);

        //pour recuperer les prayers
        String paryersString = preferences.getString("prayers", "Unknown");
        Log.d("ws commit","prayers : "+paryersString);

        // Convertir la chaîne de prières récupérée à partir de SharedPreferences en une liste d'objets PrayerModel
        List<PrayerModel> prayers = new Gson().fromJson(paryersString, new TypeToken<List<PrayerModel>>(){}.getType());

        // Obtenez l'heure actuelle
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if(currentHour>19){
            currentHour=01;
        }
        int currentMinute = calendar.get(Calendar.MINUTE);

        Log.d("WS COMMIT","heure act :"+currentHour+":"+currentMinute);


// Initialisez l'heure de la prochaine prière avec une grande valeur initiale
        int nextPrayerHour = 24; // Initialiser à une heure impossible
        int nextPrayerMinute = 60; // Initialiser à une minute impossible
        String nextPrayerName = "";
        String nextPrayerTime="";

// Parcourez la liste des prières pour trouver la prochaine prière
        for (PrayerModel prayer : prayers) {
            // Comparez l'heure actuelle avec l'heure de chaque prière
            int prayerHour = Integer.parseInt(prayer.getTime().substring(0, 2)); // Extraire l'heure de la prière
            int prayerMinute = Integer.parseInt(prayer.getTime().substring(3, 5)); // Extraire les minutes de la prière

            if (currentHour < prayerHour || (currentHour == prayerHour && currentMinute < prayerMinute)) {
                if (prayerHour < nextPrayerHour || (prayerHour == nextPrayerHour && prayerMinute < nextPrayerMinute)) {
                    nextPrayerHour = prayerHour;
                    nextPrayerMinute = prayerMinute;
                    nextPrayerName = prayer.getPrayer();
                    nextPrayerTime=prayerHour+":"+prayerMinute;
                    Log.d("WS COMMIT","next prayer :"+nextPrayerName+" time : "+nextPrayerTime);

                }
            }

            if(currentHour == prayerHour && currentMinute == prayerMinute){

                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    }
            }
        }
        views.setTextViewText(R.id.prayer,nextPrayerName);
        views.setTextViewText(R.id.time,nextPrayerTime);







        // Construct an Intent object to open MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Définir l'intention en attente pour être déclenchée lorsque le widget est cliqué
        views.setOnClickPendingIntent(R.id.mainWidgetLayout, pendingIntent);

        // Instruire le gestionnaire de widget de mettre à jour le widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WS COMMIT","mise a jour");
        super.onReceive(context, intent);
        if (intent.getAction() != null && intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            // Si l'action est ACTION_APPWIDGET_UPDATE, cela signifie que la mise à jour a été déclenchée par l'AlarmManager
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, SimpleAppWidget.class));
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

}
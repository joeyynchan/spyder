package g1436218.com.spyder.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import g1436218.com.spyder.receiver.GCMBroadcastReceiver;

public class GCMMessageHandler extends IntentService {

    String title, message;
    private Handler handler;
    public GCMMessageHandler() {
        super("GCMMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        title = message = "";
        handler = new Handler();
    }

    /**
     * This method is triggered when GCM service sends an Intent to this
     * device. Intent stores everything in the GCM message, you are free
     * to extract the content from the intent and improvise to do anything.
     *
     * Example includes toast, notification and vibration
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        title = extras.getString("title");
        message = extras.getString("message");

        vibrate(1000);

        Log.i("GCM", "Received : (" + messageType + ") \n" +
                "Title: " + title + "\nMessage = " + message);

        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void vibrate(int duration) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

}
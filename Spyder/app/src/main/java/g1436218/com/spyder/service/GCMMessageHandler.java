package g1436218.com.spyder.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;
import g1436218.com.spyder.object.Action;
import g1436218.com.spyder.receiver.GCMBroadcastReceiver;

public class GCMMessageHandler extends IntentService {

    private Handler handler;

    public GCMMessageHandler() {
        super("GCMMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();
        String action = extras.getString("action");

        if (Action.LAUNCH_APPLICATION.equals(action)) {
            launchApplication();
        } else if (Action.START_DISCOVERY.equals(action)) {
            startDiscovery();
        } else if (Action.STOP_DISCOVERY.equals(action)) {
            stopDiscovery();
        } else if (Action.FETCH_ATTENDEES.equals(action)) {
            fetchAttendees();
        } else if (Action.VIBRATE.equals(action)) {
            vibrate(extras.getInt("duration", 2000));
        } else if (Action.LONG_TOAST.equals(action)) {
            longToast(extras.getString("message", null));
        } else if (Action.SHORT_TOAST.equals(action)) {
            shortToast(extras.getString("message", null));
        } else if (Action.NOTIFICATION.equals(action)) {
            String title = extras.getString("title");
            String message = extras.getString("message");
            showNotification(title, message);
        }

        Log.i("GCM", "\n" + "Action: " + action + "\nData: " + extras.toString());

        GCMBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void showNotification(String title, String message) {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.trollface)
                    .setContentTitle(title)
                    .setContentText(message);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(GlobalConfiguration.nid++, mBuilder.build());
    }

    public void longToast(final String message) {
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void shortToast(final String message) {
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void vibrate(int duration) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    private void launchApplication() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    private void startDiscovery() {
        launchApplication();
        vibrate(GlobalConfiguration.VIBRATE_DURATION);
        Intent intent = new Intent();
        intent.setAction(Action.START_DISCOVERY);
        sendBroadcast(intent);
    }

    private void stopDiscovery() {
        launchApplication();
        Intent intent = new Intent();
        intent.setAction(Action.STOP_DISCOVERY);
        sendBroadcast(intent);
    }

    private void fetchAttendees() {
        launchApplication();
        Intent intent = new Intent();
        intent.setAction(Action.FETCH_ATTENDEES);
        sendBroadcast(intent);
    }

}
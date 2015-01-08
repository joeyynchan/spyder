package g1436218.com.spyder.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.config.GlobalConfiguration;

public abstract class BaseMainAsyncTask extends BaseAsyncTask {

    protected MainActivity activity;
    protected ProgressDialog dialog;

    protected abstract Void doInBackgroundOffline(Void... params);
    protected abstract Void doInBackgroundOnline(Void... params);

    public BaseMainAsyncTask(MainActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Context context = activity;
        SharedPreferences sharedPref = activity.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Boolean isOffline = sharedPref.getBoolean(GlobalConfiguration.OFFLINE_MODE, true);
        if (isOffline) {
            return doInBackgroundOffline();
        }
        return doInBackgroundOnline();
    }

    protected void showProgressDialog() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    protected void hideProgressDialog() {
        dialog.hide();
    }
}

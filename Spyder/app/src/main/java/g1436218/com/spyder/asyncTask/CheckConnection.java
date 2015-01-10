package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import g1436218.com.spyder.config.GlobalConfiguration;

public class CheckConnection extends AsyncTask<Void, Void, Void> {

    private Activity activity;

    public CheckConnection(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean live = false;
        try {
            SocketAddress socketAddress = new InetSocketAddress(GlobalConfiguration.IP, GlobalConfiguration.PORT);
            Socket socket = new Socket();

            int timeoutMs = 2000;
            socket.connect(socketAddress, timeoutMs);
            Log.i("SERVER_STATUS", "ONLINE");
        } catch (IOException e) {
            Log.i("SERVER_STATUS", "OFFLINE");
            new NoConnectionFragment().show(activity.getFragmentManager(), "Title");
        }
        return null;
    }


    public class NoConnectionFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("No Connection")
                    .setMessage("Server is not running at the moment.")
                    .create();
        }
    }
}

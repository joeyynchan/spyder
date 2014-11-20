package g1436218.com.spyder.asyncTask;

import g1436218.com.spyder.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.widget.TextView;

public class DisplayMacAddress extends BaseAsyncTask {

    private final String TAG = "displayMacAddress";

    public DisplayMacAddress(Activity activity) {
       super(activity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        String adapterName = adapter.getName();
        String adapterAddress = adapter.getAddress();

        TextView textview = (TextView) activity.findViewById(R.id.textview_device_information);
        textview.setText("Device name : " + adapterName + "\n Bluetooth MAC Address :" + adapterAddress);

        return null;
    }
}

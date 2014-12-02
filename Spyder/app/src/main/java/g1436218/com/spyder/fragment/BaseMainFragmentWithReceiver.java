package g1436218.com.spyder.fragment;

import android.content.BroadcastReceiver;

import g1436218.com.spyder.activity.MainActivity;

public abstract class BaseMainFragmentWithReceiver extends BaseMainFragment {

    protected BroadcastReceiver receiver;

    public BaseMainFragmentWithReceiver(MainActivity activity, int resourceId) {
        super(activity, resourceId);
    }

    @Override
    public void onResume() {
        registerReceiver(); 
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver();
        super.onPause();
    }

    protected void unregisterReceiver() {
        activity.unregisterReceiver(receiver);
    }

    protected abstract void registerReceiver();

}

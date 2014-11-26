package g1436218.com.spyder.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.FetchAttendeeList;

public abstract class BaseFragment extends Fragment {

    protected int resourceId;

    protected abstract void initializeView();

    public BaseFragment(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeView();
    }

    /* resouceId tells Fragment which layout resource file should be inflated */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(resourceId, container, false);
    }

}

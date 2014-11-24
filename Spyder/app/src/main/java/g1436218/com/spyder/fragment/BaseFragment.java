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

public class BaseFragment extends Fragment {

    protected MainActivity activity;

    public BaseFragment(MainActivity activity){
        this.activity = activity;
    }

}

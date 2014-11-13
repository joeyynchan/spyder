package g1436218.com.spyder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.object.UserMap;

/**
 * Created by Joey on 13/11/2014.
 */
public class AttendeeFragment extends BaseFragment {

    private UserMap userMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendee, container, false);

    }

    @Override
    public void onResume() {
        userMap = UserMap.getInstance();
        TextView textView = (TextView) getActivity().findViewById(R.id.textview_attendee_list);
        textView.setText("Attendee List:\n" + userMap.toString());
        super.onResume();
    }

}

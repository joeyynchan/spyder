package g1436218.com.spyder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

public class UserFragment extends Fragment {

    private MainActivity activity;
    private User user;

    TextView textview_fragment_user_name ;
    TextView textview_fragment_user_gender ;
    TextView textview_fragment_user_occupation;
    TextView textview_fragment_user_company;
    TextView textview_fragment_user_phone;
    TextView textview_fragment_user_email;
    TextView textview_fragment_user_external_link;

    public UserFragment(MainActivity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onResume() {
        textview_fragment_user_name = (TextView) getActivity().findViewById(R.id.textview_fragment_user_name);
        textview_fragment_user_name.setText(user.getName());
        textview_fragment_user_gender = (TextView) getActivity().findViewById(R.id.textview_fragment_user_gender);
        textview_fragment_user_occupation = (TextView) getActivity().findViewById(R.id.textview_fragment_user_occupation);
        textview_fragment_user_company = (TextView) getActivity().findViewById(R.id.textview_fragment_user_company);
        textview_fragment_user_email = (TextView) getActivity().findViewById(R.id.textview_fragment_user_email);
        textview_fragment_user_phone = (TextView) getActivity().findViewById(R.id.textview_fragment_user_phone);
        textview_fragment_user_external_link = (TextView) getActivity().findViewById(R.id.textview_fragment_user_external_link);
        super.onResume();
    }



}

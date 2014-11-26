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

public class UserFragment extends BaseMainFragment {

    private User user;

    TextView textview_fragment_user_name ;
    TextView textview_fragment_user_gender ;
    TextView textview_fragment_user_occupation;
    TextView textview_fragment_user_company;
    TextView textview_fragment_user_phone;
    TextView textview_fragment_user_email;
    TextView textview_fragment_user_external_link;

    public UserFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_user);
        this.user = user;
    }

    @Override
    protected void initializeView() {
        textview_fragment_user_name = (TextView) activity.findViewById(R.id.textview_fragment_user_name);
        textview_fragment_user_gender = (TextView) activity.findViewById(R.id.textview_fragment_user_gender);
        textview_fragment_user_occupation = (TextView) activity.findViewById(R.id.textview_fragment_user_occupation);
        textview_fragment_user_company = (TextView) activity.findViewById(R.id.textview_fragment_user_company);
        textview_fragment_user_email = (TextView) activity.findViewById(R.id.textview_fragment_user_email);
        textview_fragment_user_phone = (TextView) activity.findViewById(R.id.textview_fragment_user_phone);
        textview_fragment_user_external_link = (TextView) activity.findViewById(R.id.textview_fragment_user_external_link);

        textview_fragment_user_name.setText(user.getName());
    }

}

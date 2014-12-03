package g1436218.com.spyder.fragment;

import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

public class ProfileFragment extends BaseMainFragment {

    private User user;

    private TextView textview_id;
    private TextView textview_name;
    private TextView textview_occupation;
    private TextView textview_company;
    private TextView textview_gender;
    private TextView textview_phone;
    private TextView textview_email;
    private TextView textview_link;

    public ProfileFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_profile);
        this.user = user;
    }

    @Override
    protected void initializeView() {
        activity.setTitle("Profile");
        textview_name = (TextView) activity.findViewById(R.id.textview_fragment_profile_name);
        textview_gender = (TextView) activity.findViewById(R.id.textview_fragment_profile_gender);
        textview_occupation = (TextView) activity.findViewById(R.id.textview_fragment_profile_occupation);
        textview_company = (TextView) activity.findViewById(R.id.textview_fragment_profile_company);
        textview_phone = (TextView) activity.findViewById(R.id.textview_fragment_profile_phone);
        textview_email = (TextView) activity.findViewById(R.id.textview_fragment_profile_email);
        textview_link = (TextView) activity.findViewById(R.id.textview_fragment_profile_link);

        textview_name.setText(user.getName());
        textview_gender.setText(user.getGender());
        textview_occupation.setText(user.getOccupation());
        textview_company.setText(user.getCompany());
        textview_phone.setText(user.getPhone());
        textview_email.setText(user.getEmail());
        textview_link.setText(user.getExternal_link());
    }
}

package g1436218.com.spyder.fragment;

<<<<<<< HEAD
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

=======
>>>>>>> Created EditProfile fragments
import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

/**
 * Created by Joey on 01/12/2014.
 */
public class EditProfileFragment extends BaseMainFragment {

<<<<<<< HEAD
    private static String TAG = "EditProfileFragment";
    private User user;
    private EditText textview_fragment_edit_profile_name ;
    private EditText textview_fragment_edit_profile_gender ;
    private EditText textview_fragment_edit_profile_occupation;
    private EditText textview_fragment_edit_profile_company;
    private EditText textview_fragment_edit_profile_phone;
    private EditText textview_fragment_edit_profile_email;
    private EditText textview_fragment_edit_profile_link;
=======
    private User user;
>>>>>>> Created EditProfile fragments

    public EditProfileFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_edit_profile);
        this.user = user;
    }

    @Override
    protected void initializeView() {
<<<<<<< HEAD
        Log.d(TAG, "initializeView");

        textview_fragment_edit_profile_name = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_name);
        textview_fragment_edit_profile_gender = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_gender);
        textview_fragment_edit_profile_occupation = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_occupation);
        textview_fragment_edit_profile_company = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_company);
        textview_fragment_edit_profile_email = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_email);
        textview_fragment_edit_profile_phone = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_phone);
        textview_fragment_edit_profile_link = (EditText) activity.findViewById(R.id.textview_fragment_edit_profile_link);

        textview_fragment_edit_profile_name.setText(user.getName());
        textview_fragment_edit_profile_gender.setText(user.getGender());
        textview_fragment_edit_profile_occupation.setText(user.getOccupation());
        textview_fragment_edit_profile_company.setText(user.getCompany());
        textview_fragment_edit_profile_email.setText(user.getEmail());
        textview_fragment_edit_profile_phone.setText(user.getPhone());
        textview_fragment_edit_profile_link.setText(user.getExternal_link());

=======
>>>>>>> Created EditProfile fragments

    }
}

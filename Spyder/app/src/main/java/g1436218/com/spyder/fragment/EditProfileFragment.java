package g1436218.com.spyder.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.UpdateProfile;
import g1436218.com.spyder.object.User;

/**
 * Created by Joey on 01/12/2014.
 */
public class EditProfileFragment extends BaseMainFragment {

    private static String TAG = "EditProfileFragment";
    private User user;
    private EditText textview_fragment_edit_profile_name ;
    private EditText textview_fragment_edit_profile_gender ;
    private EditText textview_fragment_edit_profile_occupation;
    private EditText textview_fragment_edit_profile_company;
    private EditText textview_fragment_edit_profile_phone;
    private EditText textview_fragment_edit_profile_email;
    private EditText textview_fragment_edit_profile_link;

    public EditProfileFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_edit_profile);
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(resourceId, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        Log.d(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_edit_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                updateProfile();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initializeView() {
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
    }

    public void updateProfile(){
        Log.d(TAG, "updateProfile");
        user.setName(textview_fragment_edit_profile_name.getText().toString());
        user.setGender(textview_fragment_edit_profile_gender.getText().toString());
        user.setOccupation(textview_fragment_edit_profile_occupation.getText().toString());
        user.setCompany(textview_fragment_edit_profile_company.getText().toString());
        user.setEmail(textview_fragment_edit_profile_email.getText().toString());
        user.setPhone(textview_fragment_edit_profile_phone.getText().toString());
        user.setExternal_link(textview_fragment_edit_profile_link.getText().toString());

        new UpdateProfile((MainActivity)getActivity(), user).execute();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
    }
}

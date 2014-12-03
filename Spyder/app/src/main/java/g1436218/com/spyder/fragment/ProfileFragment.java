package g1436218.com.spyder.fragment;

import android.widget.TextView;
import android.app.Fragment;
import android.app.FragmentTransaction;
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

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

public class ProfileFragment extends BaseMainFragment {

    private static String TAG = "ProfileFragment";
    private User user;
    private final String TITLE = "Profile";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(resourceId, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){

        Log.d(TAG, "onPrepareOptions");
        super.onPrepareOptionsMenu(menu);
        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String currUsername = sharedPref.getString(activity.getString(R.string.username), "");
        if(user.getUsername().equals(currUsername)){
            menu.findItem(R.id.action_edit).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditProfile();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditProfile() {
        Log.d(TAG, "showEditProfile");
        Fragment fragment = getFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
        if (!(fragment instanceof EditProfileFragment)) {
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new EditProfileFragment((MainActivity)getActivity(), user), "CURRENT_FRAGMENT");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void initializeView() {

        activity.setTitle(TITLE);

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

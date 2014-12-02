package g1436218.com.spyder.fragment;

import android.app.Fragment;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Created EditProfile fragments
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
<<<<<<< HEAD
=======
import android.os.Bundle;
import android.view.LayoutInflater;
>>>>>>> Successfully fetch profile data from api and display in user fragment, empty string is set if profile does not exist
=======
>>>>>>> Successfully add an edit icon on the action bar in user fragment
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

public class UserFragment extends BaseMainFragment {

<<<<<<< HEAD
<<<<<<< HEAD
    private static String TAG = "UserFragment";
    private User user;

    private TextView textview_fragment_user_name ;
    private TextView textview_fragment_user_gender ;
    private TextView textview_fragment_user_occupation;
    private TextView textview_fragment_user_company;
    private TextView textview_fragment_user_phone;
    private TextView textview_fragment_user_email;
    private TextView textview_fragment_user_external_link;
=======
=======
    private static String TAG = "UserFragment";
>>>>>>> Successfully add an edit icon on the action bar in user fragment
    private User user;

    TextView textview_fragment_user_name ;
    TextView textview_fragment_user_gender ;
    TextView textview_fragment_user_occupation;
    TextView textview_fragment_user_company;
    TextView textview_fragment_user_phone;
    TextView textview_fragment_user_email;
    TextView textview_fragment_user_external_link;
>>>>>>> Successfully fetch profile data from api and display in user fragment, empty string is set if profile does not exist

    public UserFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_user);
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
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        if (!(fragment instanceof EventListFragment)) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new EditProfileFragment((MainActivity)getActivity(), user), "CURRENT_FRAGMENT");
            fragmentTransaction.commit();
        }
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
        textview_fragment_user_gender.setText(user.getGender());
        textview_fragment_user_occupation.setText(user.getOccupation());
        textview_fragment_user_company.setText(user.getCompany());
        textview_fragment_user_email.setText(user.getEmail());
        textview_fragment_user_phone.setText(user.getPhone());
        textview_fragment_user_external_link.setText(user.getExternal_link());
    }

}
package g1436218.com.spyder.fragment;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.object.User;

/**
 * Created by Joey on 01/12/2014.
 */
public class EditProfileFragment extends BaseMainFragment {

    private User user;

    public EditProfileFragment(MainActivity activity, User user) {
        super(activity, R.layout.fragment_edit_profile);
        this.user = user;
    }

    @Override
    protected void initializeView() {

    }
}

package g1436218.com.spyder.fragment;

import g1436218.com.spyder.activity.LoginActivity;

public abstract class BaseLoginFragment extends BaseFragment {

    protected LoginActivity activity;

    public BaseLoginFragment(LoginActivity activity, int resourceId) {
        super(resourceId);
        this.activity = activity;
    }

}

package g1436218.com.spyder.fragment;

import g1436218.com.spyder.activity.MainActivity;

public abstract class BaseMainFragment extends BaseFragment {

    protected MainActivity activity;

    public BaseMainFragment(MainActivity activity, int resourceId) {
        super(resourceId);
        this.activity = activity;
    }

    public MainActivity getMainActivity() {
        return activity;
    }

}

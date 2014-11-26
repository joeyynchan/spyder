package g1436218.com.spyder.asyncTask;

import g1436218.com.spyder.activity.MainActivity;

public abstract class BaseMainAsyncTask extends BaseAsyncTask {

    protected MainActivity activity;

    public BaseMainAsyncTask(MainActivity activity) {
        super();
        this.activity = activity;
    }

}

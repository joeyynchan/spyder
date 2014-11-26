package g1436218.com.spyder.asyncTask;

import g1436218.com.spyder.activity.LoginActivity;

public abstract class BaseLoginAsyncTask extends BaseAsyncTask {

    protected LoginActivity activity;

    public BaseLoginAsyncTask(LoginActivity activity) {
        super();
        this.activity = activity;
    }

}

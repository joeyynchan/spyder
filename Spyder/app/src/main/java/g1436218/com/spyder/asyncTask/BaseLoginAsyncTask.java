package g1436218.com.spyder.asyncTask;

import g1436218.com.spyder.activity.LoginActivity;

public abstract class BaseLoginAsyncTask extends BaseAsyncTask {

    protected LoginActivity activity;

    public BaseLoginAsyncTask(LoginActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (activity.getListener() != null) {
            activity.getListener().executionDone();
        }
        super.onPostExecute(v);
    }

}

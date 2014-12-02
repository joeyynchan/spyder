package g1436218.com.spyder.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.fragment.EventListFragment;

/**
 * Created by Joey on 02/12/2014.
 */
public class FetchEvents extends BaseMainAsyncTask {

    private EventListFragment fragment;

    public FetchEvents(EventListFragment fragment) {
        super(fragment.getMainActivity());
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Context context = activity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(context.getString(R.string.username), "");

        //TODO


        return null;
    }
}

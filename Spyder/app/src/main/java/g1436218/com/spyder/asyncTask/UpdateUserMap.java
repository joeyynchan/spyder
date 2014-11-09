package g1436218.com.spyder.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.backbone.JSONParser;
import g1436218.com.spyder.backbone.QueryExecutor;
import g1436218.com.spyder.object.UserMap;

public class UpdateUserMap extends BaseAsyncTask {

    Activity activity;
    UserMap userMap;
    TextView textView;

    public UpdateUserMap(Activity activity){
        super(activity);
        this.userMap = UserMap.getInstance();
        this.textView = (TextView) activity.findViewById(R.id.textView3);
    }

    @Override
    protected Void doInBackground(Void... params) {
        textView.setText("YOLO");
        //userMap.updateList(JSONParser.toJSONObject(QueryExecutor.test()));
        return null;
    }
    
    @Override
    protected void onPostExecute(Void result) {
        TextView textView = (TextView) activity.findViewById(R.id.textView3);
        textView.setText(QueryExecutor.test());

    }
}

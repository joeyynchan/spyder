package g1436218.com.spyder.dialogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import g1436218.com.spyder.R;
import g1436218.com.spyder.asyncTask.SendMessage;

public class MessageFragment extends DialogFragment implements View.OnClickListener {

    private String gcm_id;
    private String recipient;

    private Button cancel;
    private Button send;
    private EditText message;
    private EditText title;

    public MessageFragment(String recipient, String gcm_id) {
        this.gcm_id = gcm_id;
        this.recipient = recipient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Send Message to " + recipient);

        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        cancel = (Button) rootView.findViewById(R.id.button_fragment_message_cancel);
        cancel.setOnClickListener(this);
        send = (Button) rootView.findViewById(R.id.button_fragment_message_send);
        send.setOnClickListener(this);

        //title = (EditText) rootView.findViewById(R.id.edittext_fragment_message_title);
        message = (EditText) rootView.findViewById(R.id.edittext_fragment_message_message);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_message_cancel: {
                this.dismiss();
                break;
            }
            case R.id.button_fragment_message_send: {
                Context context = getActivity();
                SharedPreferences sharedPref = getActivity().getSharedPreferences(
                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String sender = sharedPref.getString("name", "");

                //String _title = title.getText().toString();
                String _message = message.getText().toString();
                new SendMessage("", _message, sender, gcm_id).execute();
                this.dismiss();
            }
        }
    }
}

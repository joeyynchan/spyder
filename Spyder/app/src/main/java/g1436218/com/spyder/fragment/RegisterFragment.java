package g1436218.com.spyder.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.asyncTask.CreateAccount;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Button register_button_register = (Button) rootView.findViewById(R.id.button_fragment_register_register);
        register_button_register.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        new CreateAccount((LoginActivity)this.getActivity()).execute();
    }
}

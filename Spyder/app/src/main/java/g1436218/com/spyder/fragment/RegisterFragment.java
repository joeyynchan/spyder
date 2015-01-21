package g1436218.com.spyder.fragment;

import android.view.View;
import android.widget.Button;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.LoginActivity;
import g1436218.com.spyder.asyncTask.CreateAccount;

public class RegisterFragment extends BaseLoginFragment implements View.OnClickListener {

    private final String TAG = "RegisterFragment";

    public RegisterFragment(LoginActivity activity) {
        super(activity, R.layout.fragment_register);

    }

    @Override
    public void onClick(View v) {
        new CreateAccount((LoginActivity)this.getActivity()).execute();
    }

    @Override
    protected void initializeView() {
        Button register_button_register = (Button) activity.findViewById(R.id.button_fragment_register_register);
        register_button_register.setOnClickListener(this);
    }
}

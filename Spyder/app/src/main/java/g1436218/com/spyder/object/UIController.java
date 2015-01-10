package g1436218.com.spyder.object;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import g1436218.com.spyder.R;
import g1436218.com.spyder.activity.MainActivity;
import g1436218.com.spyder.asyncTask.DisplayProfile;
import g1436218.com.spyder.asyncTask.FetchAttendees;
import g1436218.com.spyder.fragment.AttendeeFragment;
import g1436218.com.spyder.fragment.EventListFragment;
import g1436218.com.spyder.fragment.InteractionFragment;
import g1436218.com.spyder.fragment.LogoutFragment;
import g1436218.com.spyder.fragment.ReceiveMessageFragment;

public class UIController {

    MainActivity activity;
    public static final int DISCOVERY_ON = 1;

    private LinearLayout button_attendee_list;
    private LinearLayout button_event_list;
    private LinearLayout button_interactions;
    private LinearLayout button_profile;

    private ImageView imageview_attendee_list;
    private ImageView imageview_event_list;
    private ImageView imageview_interactions;
    private ImageView imageview_profile;

    private TextView textview_attendee_list;
    private TextView textview_event_list;
    private TextView textview_interatcions;
    private TextView textview_profile;

    private ImageView imageview_status;

    public UIController(MainActivity activity) {

        this.activity = activity;

        button_attendee_list = (LinearLayout) activity.findViewById(R.id.button_attendee_list);
        button_interactions = (LinearLayout) activity.findViewById(R.id.button_interactions);
        button_event_list = (LinearLayout) activity.findViewById(R.id.button_event_list);
        button_profile = (LinearLayout) activity.findViewById(R.id.button_profile);

        imageview_attendee_list = (ImageView) activity.findViewById(R.id.button_attendee_list_icon);
        imageview_interactions = (ImageView) activity.findViewById(R.id.button_interactions_icon);
        imageview_event_list = (ImageView) activity.findViewById(R.id.button_event_list_icon);
        imageview_profile = (ImageView) activity.findViewById(R.id.button_profile_icon);

        textview_attendee_list = (TextView) activity.findViewById(R.id.button_attendee_list_text);
        textview_interatcions = (TextView) activity.findViewById(R.id.button_interactions_text);
        textview_event_list = (TextView) activity.findViewById(R.id.button_event_list_text);
        textview_profile = (TextView) activity.findViewById(R.id.button_event_profile_text);


        imageview_status = (ImageView) activity.findViewById(R.id.main_activity_status);
        setStatus(BluetoothAdapter.getDefaultAdapter().getScanMode());
    }

    public boolean showOptionMenu(Menu menu) {
        menu.findItem(R.id.action_start_bluetooth).setVisible(false);
        menu.findItem(R.id.action_stop_bluetooth).setVisible(false);
        menu.findItem(R.id.action_set_discoverable).setVisible(false);
        menu.findItem(R.id.action_start_discovery).setVisible(false);
        menu.findItem(R.id.action_stop_discovery).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_done).setVisible(false);

        if (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF) {
            menu.findItem(R.id.action_start_bluetooth).setVisible(true);
        } else {
            menu.findItem(R.id.action_stop_bluetooth).setVisible(true);
        }
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE) {
            menu.findItem(R.id.action_set_discoverable).setVisible(true);
        }
        if (BluetoothAdapter.getDefaultAdapter().getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            BluetoothController bluetoothController = activity.getBluetoothController();
            if (bluetoothController.isDiscovery()) {
                menu.findItem(R.id.action_stop_discovery).setVisible(true);
            } else {
                menu.findItem(R.id.action_start_discovery).setVisible(true);
            }
        }
        return true;
    }

    public void showAttendees() {
        new FetchAttendees(activity).execute();
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new AttendeeFragment(activity), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
        resetButtonState();
        button_attendee_list.setClickable(false);
        imageview_attendee_list.setImageResource(R.drawable.main_activity_attendee_list_pressed);
        textview_attendee_list.setTextColor(activity.getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    public void showEventList() {
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new EventListFragment(activity), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
        Intent intent = new Intent();
        intent.setAction(Action.FETCH_EVENTS);
        activity.sendBroadcast(intent);
        resetButtonState();
        button_event_list.setClickable(false);
        imageview_event_list.setImageResource(R.drawable.main_activity_event_list_pressed);
        textview_event_list.setTextColor(activity.getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    public void showInteractions() {
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new InteractionFragment(activity), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
        resetButtonState();
        button_interactions.setClickable(false);
        imageview_interactions.setImageResource(R.drawable.main_activity_interactions_icon_pressed);
        textview_interatcions.setTextColor(activity.getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    public void showProfile() {
        new DisplayProfile(activity).execute();
        resetButtonState();
        button_profile.setClickable(false);
        imageview_profile.setImageResource(R.drawable.main_activity_profile_pressed);
        textview_profile.setTextColor(activity.getResources().getColor(R.color.main_activity_button_text_pressed));
    }

    public void logout() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        new LogoutFragment().show(fragmentManager, "Logout");
    }

    public void showMessage(String sender, String title, String message) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        new ReceiveMessageFragment(sender, title, message).show(fragmentManager, "showMessage");
    }

    private void resetButtonState() {
        imageview_attendee_list.setImageResource(R.drawable.main_activity_attendee_list_normal);
        imageview_event_list.setImageResource(R.drawable.main_activity_event_list_normal);
        imageview_interactions.setImageResource(R.drawable.main_activity_interactions_icon_normal);
        imageview_profile.setImageResource(R.drawable.main_activity_profile_normal);

        textview_attendee_list.setTextColor(activity.getResources().getColor(R.color.textedit_background));
        textview_event_list.setTextColor(activity.getResources().getColor(R.color.textedit_background));
        textview_interatcions.setTextColor(activity.getResources().getColor(R.color.textedit_background));
        textview_profile.setTextColor(activity.getResources().getColor(R.color.textedit_background));

        button_attendee_list.setClickable(true);
        button_event_list.setClickable(true);
        button_interactions.setClickable(true);
        button_profile.setClickable(true);
    }

    public void setStatus(int status) {
        switch (status) {
            case DISCOVERY_ON:
                imageview_status.setImageResource(R.drawable.main_activity_status_discovery_on);
                break;
            case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                imageview_status.setImageResource(R.drawable.main_activity_status_discoverable);
                break;
            case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                imageview_status.setImageResource(R.drawable.main_activity_status_discovering);
                break;
            default:
                imageview_status.setImageResource(R.drawable.main_activity_status_normal);
                break;
        }
    }

}

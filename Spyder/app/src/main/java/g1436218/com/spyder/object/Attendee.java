package g1436218.com.spyder.object;

import android.graphics.Bitmap;

public class Attendee {

    String name;
    String username;
    String macAddress;
    String gcm_id;
    String photo_url;
    private Bitmap photo;

    public Attendee(String macAddress, String username, String name, String gcm_id, String photo_url) {
        this.macAddress = macAddress;
        this.username = username;
        this.name = name;
        this.gcm_id = gcm_id;
        this.photo_url = photo_url;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {return name;}

    public String getGcm_id() {
        return gcm_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public Bitmap getPhoto(){
        return photo;
    }

    public void setPhoto (Bitmap photo){
        this.photo = photo;
    }
    public String toString() {
        return macAddress + " : " + username;
    }

    public boolean containKeyword(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase());
    }

}

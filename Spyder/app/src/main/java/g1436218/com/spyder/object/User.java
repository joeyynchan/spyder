package g1436218.com.spyder.object;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {

    private final String EMPTY = "";

    private String username;
    private String name;
    private String gender;
    private String occupation;
    private String company;
    private String email;
    private String phone;
    private String external_link;
    private String photoURL;
    private Bitmap photo;
    private ArrayList<String> connections;

    public User(String username) {
        this.username = username;
        this.name = EMPTY;
        this.gender = EMPTY;
        this.occupation = EMPTY;
        this.company = EMPTY;
        this.email = EMPTY;
        this.phone = EMPTY;
        this.external_link = EMPTY;
        this.photoURL = EMPTY;
        this.connections = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExternal_link() {
        return external_link;
    }

    public void setExternal_link(String external_link) {
        this.external_link = external_link;
    }

    public String getPhotoURL(){
        return photoURL;
    }

    public void setPhotoURL(String photoURL){
        this.photoURL = photoURL;
    }

    public Bitmap getPhoto(){
        return photo;
    }

    public void setPhoto(Bitmap photo){
        this.photo = photo;
    }

    public void addConnection(String username) {
        connections.add(username);
    }
}

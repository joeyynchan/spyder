package com.journaldev.mongodb.model;

import java.lang.String;

public class Profile {

    private String id;
    private String user_name;
    private String name;
    private String company;
    private String photo;
    private String email;
    private String phone;
    private String external_link;
    private String gender;
    private List<String> connections;

    public Profile(String user_name, String name, String company,
                   String photo, String email, String phone,
                   String external_link, String gender, List<String> connections) {
        this.user_name = user_name;
        this.name = name;
        this.company = company;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.external_link = external_link;
        this.gender = gender;
        this.connections = connections;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getConnections() {
        return connections;
    }

    public void setConnections(List<String> connections) {
        this.connections = connections;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

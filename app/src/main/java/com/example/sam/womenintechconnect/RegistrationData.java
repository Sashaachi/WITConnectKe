package com.example.sam.womenintechconnect;

public class RegistrationData {
    private String id;
    private String Name;
    private String Email;
    private String Profession;
    private String Role;


    public RegistrationData(){ }

    public RegistrationData(String id, String name, String email, String profession, String role) {
        this.id = id;
        Name = name;
        Email = email;
        Profession = profession;
        Role = role;
    }

    public RegistrationData(String name, String email, String profession) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}

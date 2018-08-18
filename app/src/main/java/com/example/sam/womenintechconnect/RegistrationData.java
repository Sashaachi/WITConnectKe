package com.example.sam.womenintechconnect;

public class RegistrationData {
    //public String id;
    private String Name;
    private String Email;
    private String Profession;
    private String Role;



    RegistrationData(String name, String email, String profession, String role) {
        Name = name;
        Email = email;
        Profession = profession;
        Role=role;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
    public String getProfession() {
        return Profession;
    }

    public String getRole() {
        return Role;
    }
}

package services.model;

import java.sql.Date;

/**
 * Created by listman on 5/4/16.
 */
public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String sexo;
    public Date birthDay;
    public Resolution resolution;
    public Device device;
    public Platform platform;
    public OsVersion osVersion;
    public String address;

    public User(int id, String firstName, String lastName, String email, String phone, String sexo, Date birthDay,String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.sexo = sexo;
        this.birthDay = birthDay;
        this.address = address;
    }
}

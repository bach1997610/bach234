/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author binhm
 */
public class Patient implements Serializable {

    private static final long serialversionUID = 129348938L;

    private SimpleStringProperty id;        //ID
    private SimpleStringProperty firstName; //Họ
    private SimpleStringProperty lastName;  //Tên
    private SimpleStringProperty email;     //
    private SimpleStringProperty phone;     //
    private SimpleStringProperty sex;
    private SimpleStringProperty dob;       //Ngày sinh

    public Patient() {
        id = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        sex = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        email = new SimpleStringProperty();
        phone = new SimpleStringProperty();
    }

    //Constructor with firstName, lastName, sex and dob
    public Patient(String fn, String ln, String s, String d) {
        id = new SimpleStringProperty();
        firstName = new SimpleStringProperty(fn);
        lastName = new SimpleStringProperty(ln);
        sex = new SimpleStringProperty(s);
        dob = new SimpleStringProperty(d);
        email = new SimpleStringProperty();
        phone = new SimpleStringProperty();
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(getId());
        out.writeObject(getFirstName());
        out.writeObject(getLastName());
        out.writeObject(getDob());
        out.writeObject(getSex());
        out.writeObject(getPhone());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        id = new SimpleStringProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        sex = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        email = new SimpleStringProperty();
        phone = new SimpleStringProperty();

        String s = (String) in.readObject();
                setId(s);
        s = (String) in.readObject();
        setFirstName(s);
        

        s = (String) in.readObject();
        setLastName(s);
        s = (String) in.readObject();
        setDob(s);
        s = (String) in.readObject();
        setSex(s);
        s = (String) in.readObject();
        setPhone(s);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getDob() {
        return dob.get();
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

}

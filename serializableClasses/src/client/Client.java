package client;

import java.io.Serializable;
import java.sql.Date;

public class Client implements Serializable{

    public Client(String name, String passportSeria, Integer passportNumber) {
        this.name = name;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirth() {
        return birth;
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public Integer getPassportNumber() {
        return passportNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    private int id;
    private String name;
    private Date birth;
    private String passportSeria;
    private Integer passportNumber;
    private String phone;
    private String address;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public void setPassportNumber(Integer passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Client(int id, String name, Date birth, String passportSeria, Integer passportNumber, String phone, String address) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
        this.phone = phone;
        this.address = address;
    }

    public Client(String name, Date birth, String passportSeria, Integer passportNumber, String phone, String address) {
        this.name = name;
        this.birth = birth;
        this.passportSeria = passportSeria;
        this.passportNumber = passportNumber;
        this.phone = phone;
        this.address = address;
    }
}

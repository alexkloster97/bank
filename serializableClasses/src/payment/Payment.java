package payment;

import client.Client;
import user.User;

import java.io.Serializable;

public class Payment implements Serializable{
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentType getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(PaymentType payment_type) {
        this.payment_type = payment_type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getSumm() {
        return summ;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String client) {
        this.clientName = client;
    }

    private Integer id;
    private String description;
    private PaymentType payment_type;
    private Integer number;
    private Double summ;
    private Integer client_id;
    private String clientName;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser(String user) {
        this.userName = user;
    }

    private Integer user_id;
    private String userName;

    public Payment(Integer id, String description, PaymentType payment_type_id, Integer number, Double summ, Integer client_id) {
        this.id = id;
        this.description = description;
        this.payment_type = payment_type_id;
        this.number = number;
        this.summ = summ;
        this.client_id = client_id;
    }

    public Payment(String description, PaymentType payment_type_id, Integer number, Double summ, Integer client_id) {
        this.description = description;
        this.payment_type = payment_type_id;
        this.number = number;
        this.summ = summ;
        this.client_id = client_id;
    }

    public Payment(Integer id, String description, PaymentType payment_type, Integer number, Double summ, Integer client_id, String clientName, Integer user_id, String userName) {
        this.id = id;
        this.description = description;
        this.payment_type = payment_type;
        this.number = number;
        this.summ = summ;
        this.client_id = client_id;
        this.clientName = clientName;
        this.user_id = user_id;
        this.userName = userName;
    }


    public Payment(String description, PaymentType payment_type, Integer number, Double summ, Client client, User user) {
        this.description = description;
        this.payment_type = payment_type;
        this.number = number;
        this.summ = summ;
        this.client_id = client.getId();
        this.clientName = client.getName();
        this.user_id = user.getId();
        this.userName = user.getName();
    }
}

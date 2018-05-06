package deposit;

import client.Client;
import user.User;

import java.io.Serializable;
import java.sql.Date;

public class Deposit implements Serializable {

    private Integer id;
    private DepositType deposit;
    private Double summ;
    private Date startDate;
    private Date endDate;
    private Integer term;
    private Client client;
    private User user;

    public Deposit(Integer id, DepositType deposit, Double summ, Date startDate, Date endDate, Integer term, Client client, User user) {
        this.id = id;
        this.deposit = deposit;
        this.summ = summ;
        this.startDate = startDate;
        this.endDate = endDate;
        this.term = term;
        this.client = client;
        this.user = user;
    }

    public Deposit(DepositType deposit, Double summ, Date startDate, Date endDate, Integer term, Client client, User user) {
        this.deposit = deposit;
        this.summ = summ;
        this.startDate = startDate;
        this.endDate = endDate;
        this.term = term;
        this.client = client;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DepositType getDeposit() {
        return deposit;
    }

    public void setDeposit(DepositType deposit) {
        this.deposit = deposit;
    }

    public Double getSumm() {
        return summ;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

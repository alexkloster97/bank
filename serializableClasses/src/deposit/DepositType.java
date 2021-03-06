package deposit;

import java.io.Serializable;

public class DepositType implements Serializable {
    private Integer id;
    private String description;
    private Double percentage;
    private Integer term;
    private Double min_summ;
    private Integer currency_id;
    private Boolean capitalization;
    private String currency;

    public DepositType(Integer id, String description, Double percentage, Integer term, Double min_summ, Integer currency_id, Boolean capitalization, String currency) {
        this.id = id;
        this.description = description;
        this.percentage = percentage;
        this.term = term;
        this.min_summ = min_summ;
        this.currency_id = currency_id;
        this.capitalization = capitalization;
        this.currency = currency;
    }

    public DepositType(String description, Double percentage, Integer term, Double min_summ, Integer currency_id, Boolean capitalization, String currency) {
        this.description = description;
        this.percentage = percentage;
        this.term = term;
        this.min_summ = min_summ;
        this.currency_id = currency_id;
        this.capitalization = capitalization;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getMin_summ() {
        return min_summ;
    }

    public void setMin_summ(Double min_summ) {
        this.min_summ = min_summ;
    }

    public Integer getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Integer currency_id) {
        this.currency_id = currency_id;
    }

    public Boolean getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(Boolean capitalization) {
        this.capitalization = capitalization;
    }


    @Override
    public String toString() {
        return description;
    }
}

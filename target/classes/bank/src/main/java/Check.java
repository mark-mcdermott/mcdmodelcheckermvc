package main.java;

import java.util.Date;

public class Check {

    String depAcctNo;
    Integer checkNo;
    Date date;
    Float amount;
    String name;
    String accountNo;
    String routingNo;

    public Check(String depAcctNo, Integer checkNo, Date date, Float amount, String name, String accountNo, String routingNo) {
        this.depAcctNo = depAcctNo;
        this.checkNo = checkNo;
        this.date = date;
        this.amount = amount;
        this.name = name;
        this.accountNo = accountNo;
        this.routingNo = routingNo;
    }

    public String getDepAcctNo() {
        return depAcctNo;
    }

    public Float getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Integer getCheckNo() {
        return checkNo;
    }

    public String getRoutingNo() {
        return routingNo;
    }

    public String getName() {
        return name;
    }
}

package main.java;

import java.util.Date;

public class Transfer {

    Date timestamp;
    String fromAcct;
    String toAcct;
    Float amount;

    public Transfer(Date timestamp, String fromAcct, String toAcct, Float amount) {
        this.timestamp = timestamp;
        this.fromAcct = fromAcct;
        this.toAcct = toAcct;
        this.amount = amount;
    }

    public Float getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getFromAcct() {
        return fromAcct;
    }

    public String getToAcct() {
        return toAcct;
    }
}

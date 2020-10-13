package main.java;

import java.security.Timestamp;

public class AtmCheck {

    Timestamp timestamp;
    Check check;
    String toAcct;

    public AtmCheck(Timestamp timestamp, Check check, String toAcct) {
        this.timestamp = timestamp;
        this.check = check;
        this.toAcct = toAcct;
    }

}

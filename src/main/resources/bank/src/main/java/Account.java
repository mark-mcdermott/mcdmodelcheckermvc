package main.java;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Account {

    String accountNo;
    Float balance;

    public Account(String accountNo, Float balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return decimalFormat.format(balance);
    }

    public void withdraw(Float amount) {
        this.balance = this.balance - amount;
    }

    public void deposit(Float amount) {
        this.balance = this.balance + amount;
    }

    public void depositCheck(Check check) { this.balance = this.balance + check.amount; }

    public String getAccountNo() {
        return accountNo;
    }

    public Float getBalance() {
        return balance;
    }

}

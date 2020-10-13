package main.java;

// bank idea is loosely based on https://github.com/sabaelhilo/BankAccountExample
// the parallel thread code approach is based on https://stackoverflow.com/a/32577206

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Bank {

    List<Account> accounts;
    String checksFilePath;
    String transfersFilePath;

    public Bank(String accountsFilePath, String checksFilePath, String transfersFilePath) throws IOException, ParseException {
        initAccounts(accountsFilePath);
        initFilePaths(checksFilePath, transfersFilePath);
    }

    void initAccounts(String accountsFilePath) throws IOException, ParseException {
        List<String> csvRows = csvRows(accountsFilePath);
        List<Account> accounts = accountsFromCsvRows(csvRows);
        this.accounts = accounts;
    }

    void initFilePaths(String checksFilePath, String transfersFilePath) {
        this.checksFilePath = checksFilePath;
        this.transfersFilePath = transfersFilePath;
    }

    List<Account> accountsFromCsvRows(List<String> rows) throws ParseException {
        List<Account> accounts = new ArrayList<>();
        for (String row : rows) {
            String[] acctElems = row.split(",");
            String acctNo = acctElems[0];
            Float balance = Float.parseFloat(acctElems[1]);
            Account account = new Account(acctNo, balance);
            accounts.add(account);
        }
        return accounts;
    }

    // utilities

    // read csv code from https://stackabuse.com/reading-and-writing-csvs-in-java/
    public List<String> csvRows (String filepath) throws IOException {
        List<String> csvRows = new ArrayList<String>();
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            if (i != 0) { // Skip the first line, the headers line. The data rows start on line two.
                csvRows.add(row);
            }
            i++;
        }
        csvReader.close();
        return csvRows;
    }

    public Boolean isMember(String acctNo) {
        for (Account acct : accounts) {
            if (acct.getAccountNo().equals(acctNo)) {
                return true;
            }
        }
        return false;
    }

    public Account getAcct(String accountNo) throws BankException {
        for (Account account : accounts) {
            if (account.getAccountNo().equals(accountNo)) {
                return account;
            }
        }
        throw new BankException("Account " + accountNo + " not found in accounts at Bank.java:104");
    }

    // str to date from https://www.baeldung.com/java-string-to-date#:~:text=Let's%20see%20how%20to%20convert,MMM%2Dyyyy%22%2C%20Locale.
    Date dmyyStrToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("d/m/yy", Locale.ENGLISH);
        Date date = formatter.parse(str);
        return date;
    }

    // str to date code approach from https://www.baeldung.com/java-string-to-date#:~:text=Let's%20see%20how%20to%20convert,MMM%2Dyyyy%22%2C%20Locale.
    Date mmmdyyyykkmmssStrToDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy kk:mm:ss", Locale.ENGLISH);
        Date date = formatter.parse(str);
        return date;
    }

}

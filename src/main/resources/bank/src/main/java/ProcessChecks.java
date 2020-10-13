package main.java;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static java.lang.Integer.parseInt;

// a callable's constructor with arguments code approach from https://stackoverflow.com/a/9993101
public class ProcessChecks implements Callable<Object>  {

    Bank bank;
    String checksFilepath;

    @Override
    public Object call() throws Exception {
        List<Check> checks = getChecksList(checksFilepath);
        depositChecks(checks);
        System.out.println("done processing checks");
        return null;
    }

    public ProcessChecks(String checksFilepath, Bank bank) throws IOException, ParseException, BankException {
        this.bank = bank;
        this.checksFilepath = checksFilepath;
    }

    List<Check> getChecksList(String checksFilepath) throws IOException, ParseException {
        List<String> csvRows = bank.csvRows(checksFilepath);
        List<Check> checks = checksFromCsvRows(csvRows);
        return checks;
    }

    List<Check> checksFromCsvRows(List<String> rows) throws ParseException {
        List<Check> checks = new ArrayList<>();
        for (String row : rows) {
            String[] checkElems = row.split(",");
            String depAcctNo = checkElems[0];
            Integer checkNo = parseInt(checkElems[1]);
            Date date = bank.dmyyStrToDate(checkElems[2]);
            Float amount = Float.parseFloat(checkElems[3]);
            String name = checkElems[4];
            String checkAcctNo = checkElems[5];
            String routingNo = checkElems[6];
            Check check = new Check(depAcctNo, checkNo, date, amount, name, checkAcctNo, routingNo);
            checks.add(check);
        }
        return checks;
    }

    void depositChecks(List<Check> checks) throws BankException {
        for (Check check : checks) {
            depositCheck(check);
        }
    }

    void depositCheck(Check check) throws BankException {
        Account acct = bank.getAcct(check.getDepAcctNo());
        acct.depositCheck(check);
    }

}

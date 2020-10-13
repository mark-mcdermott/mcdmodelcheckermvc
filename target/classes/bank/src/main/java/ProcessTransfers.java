package main.java;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

// a callable's constructor with arguments code approach from https://stackoverflow.com/a/9993101
public class ProcessTransfers implements Callable<Object> {

    Bank bank;
    String transfersFilepath;

    @Override
    public Object call() throws Exception {
        List<Transfer> transfers = getTransfersList(transfersFilepath);
        processTransfers(transfers);
        System.out.println("done processing transfers");
        return null;
    }

    public ProcessTransfers (String transfersFilepath, Bank bank) {
        this.bank = bank;
        this.transfersFilepath = transfersFilepath;
    }

    public void processTransfers(List<Transfer> transfers) throws BankException {
        for (Transfer transfer : transfers) {
            processTransfer(transfer);
        }
    }

    public void processTransfer(Transfer transfer) throws BankException {
        String fromAcctNo = transfer.getFromAcct();
        String toAcctNo = transfer.getToAcct();
        Float amount = transfer.getAmount();
        if (bank.isMember(fromAcctNo)) {
            Account fromAcct = bank.getAcct(fromAcctNo);
            fromAcct.withdraw(amount);
        }
        if (bank.isMember(toAcctNo)) {
            Account toAcct = bank.getAcct(toAcctNo);
            toAcct.deposit(amount);
        }
    }

    List<Transfer> getTransfersList(String transfersFilepath) throws ParseException, IOException {
        List<String> csvRows = bank.csvRows(transfersFilepath);
        List<Transfer> transfers = transfersFromCsvRows(csvRows);
        return transfers;
    }

    List<Transfer> transfersFromCsvRows(List<String> rows) throws ParseException {
        List<Transfer> transfers = new ArrayList<>();
        for (String row : rows) {
            String[] transElems = row.split(",");
            Date date = bank.mmmdyyyykkmmssStrToDate(transElems[0]);
            String fromAcctNo = transElems[1];
            String toAcctNo = transElems[2];
            Float amount = Float.parseFloat(transElems[3]);
            Transfer transfer = new Transfer(date, fromAcctNo, toAcctNo, amount);
            transfers.add(transfer);
        }
        return transfers;
    }

}

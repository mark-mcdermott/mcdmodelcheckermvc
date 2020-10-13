package main.java;

import main.java.Bank;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) throws IOException, ParseException, BankException, InterruptedException, ExecutionException {

        // csv paths all set here
        String csvPath = "./src/main/resources/csv/";
        String accountsFilepath = csvPath + "accounts.csv";
        String checksFilepath = csvPath + "checks.csv";
        String transfersFilepath = csvPath + "transfers.csv";

        // instantiate a Bank object. here it just inits the checking accounts.
        Bank bank = new Bank(accountsFilepath, checksFilepath, transfersFilepath);

        // this runs processATMChecks and processTransfers in parallel
        // executorservice code from https://stackoverflow.com/a/12191090
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new ProcessChecks(checksFilepath, bank));
        executor.submit(new ProcessTransfers(transfersFilepath, bank));
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
        System.exit(0);
    }

}

package org.example;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, InterruptedException, KeyManagementException {
        ReadCSV readCSV = new ReadCSV();
        readCSV.readFromCsv();
    }
}
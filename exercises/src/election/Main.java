package election;

import java.text.NumberFormat;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Election election = new Election(1000, 800, 150, 50);

        ElectionReport electionReport = new ElectionReport(election);
        electionReport.printReport();
    }

}
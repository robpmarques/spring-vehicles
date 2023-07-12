package election;

import java.text.NumberFormat;

public class ElectionReport {
    Election election;
    private final NumberFormat percentage;

    public ElectionReport(Election election) {
        this.election = election;
        percentage = NumberFormat.getPercentInstance();
    }

    public void printReport() {
        var nullVotes = percentage.format(election.calculateNullVotesPercentage());
        var whiteVotes = percentage.format(election.calculateWhiteVotesPercentage());
        var validVotes = percentage.format(election.calculateValidVotesPercentage());

        System.out.println("Resultado da Eleição");
        System.out.println("--------");

        System.out.println(String.format("Total de votos: %s", election.getTotalVotes()));
        System.out.println();
        System.out.println(String.format("Porcentagem de votos válidos: %s", validVotes));
        System.out.println(String.format("Porcentagem de votos brancos: %s", whiteVotes));
        System.out.println(String.format("Porcentagem de votos nulos: %s", nullVotes));
    }
}

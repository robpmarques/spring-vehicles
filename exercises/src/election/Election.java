package election;

public class Election {
    private final double totalVotes;
    private final double validVotes;
    private final double whiteVotes;
    private final double nullVotes;

    public Election(int totalVotes, int validVotes, int whiteVotes, int nullVotes) {
        this.totalVotes = totalVotes;
        this.validVotes = validVotes;
        this.whiteVotes = whiteVotes;
        this.nullVotes = nullVotes;
    }

    public double calculateValidVotesPercentage() {
        return (validVotes / totalVotes);
    }

    public double calculateWhiteVotesPercentage() {
        return (whiteVotes / totalVotes);
    }

    public double calculateNullVotesPercentage() {
        return (nullVotes / totalVotes);
    }

    public double getTotalVotes() {
        return totalVotes;
    }
}

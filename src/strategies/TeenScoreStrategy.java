package strategies;

import java.util.List;

public final class TeenScoreStrategy implements AverageScoreStrategy {
    @Override
    public Double getAverageScore(final List<Double> niceScores) {
        double scoreSum = 0d;
        int sum = 0;
        for (int i = 0; i < niceScores.size(); i++) {
            scoreSum += (niceScores.get(i) * (i + 1));
            sum += i + 1;
        }
        return scoreSum / sum;
    }
}

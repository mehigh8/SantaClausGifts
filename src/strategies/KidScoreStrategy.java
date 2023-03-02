package strategies;

import java.util.List;

public final class KidScoreStrategy implements AverageScoreStrategy {
    @Override
    public Double getAverageScore(final List<Double> niceScores) {
        Double sum = 0d;
        for (Double score : niceScores) {
            sum += score;
        }
        return sum / niceScores.size();
    }
}

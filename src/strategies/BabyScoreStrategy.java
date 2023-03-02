package strategies;

import common.Constants;

import java.util.List;

public final class BabyScoreStrategy implements AverageScoreStrategy {
    @Override
    public Double getAverageScore(final List<Double> niceScores) {
        return Constants.PERFECT_SCORE;
    }
}

package factories;

import common.Constants;
import strategies.AverageScoreStrategy;
import strategies.BabyScoreStrategy;
import strategies.KidScoreStrategy;
import strategies.TeenScoreStrategy;

/**
 * Clasa factory pentru strategii de calculare a scorului de cumintenie.
 */
public final class AverageScoreStrategyFactory {
    private static AverageScoreStrategyFactory instance = null;
    private AverageScoreStrategyFactory() {
    }

    /**
     * Functie care returneaza instanta factory-ului.
     * @return instanta
     */
    public static AverageScoreStrategyFactory getInstance() {
        if (instance == null) {
            instance = new AverageScoreStrategyFactory();
        }
        return instance;
    }

    /**
     * Functie care genereaza o strategie corespunzatoare varstei primite.
     * @param age varsta copilului
     * @return strategia
     */
    public AverageScoreStrategy createStrategy(final int age) {
        if (age < Constants.KID_AGE) {
            return new BabyScoreStrategy();
        } else if (age < Constants.TEEN_AGE) {
            return new KidScoreStrategy();
        }
        return new TeenScoreStrategy();
    }
}

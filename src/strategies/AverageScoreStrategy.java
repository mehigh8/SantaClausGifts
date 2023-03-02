package strategies;

import java.util.List;

/**
 * Interfata folosita pentru implementarea mai multor strategii care decid modul ce calculare al
 * scorului de cumintenie al unui copil.
 */
public interface AverageScoreStrategy {
    /**
     * Functie care calculeaza scorul de cumintenie.
     * @param niceScores lista de scoruri ale copilului
     * @return scorul de cumintenie calculat
     */
    Double getAverageScore(List<Double> niceScores);
}

package com.brito.macena.boteco.utils;

import com.bueno.spi.model.GameIntel;

public class Game {
    public static boolean wonFirstRound(GameIntel intel) {
        if (!intel.getRoundResults().isEmpty()) return intel.getRoundResults().get(0) == GameIntel.RoundResult.WON;
        return false;
    }

    public static boolean lostFirstRound(GameIntel intel) {
        if (!intel.getRoundResults().isEmpty()) return intel.getRoundResults().get(0) == GameIntel.RoundResult.LOST;
        return false;
    }

    public static boolean hasManilha(GameIntel intel) {
        return intel.getCards().stream()
                .anyMatch(card -> card.isManilha(intel.getVira()));
    }

    public static boolean isCriticalSituation(GameIntel intel) {
        int scoreDifference = intel.getOpponentScore() - intel.getScore();
        int handPoints = intel.getHandPoints();

        return scoreDifference >= 6 || handPoints >= 6;
    }
}

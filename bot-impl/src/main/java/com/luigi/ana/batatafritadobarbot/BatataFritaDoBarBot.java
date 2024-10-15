package com.luigi.ana.batatafritadobarbot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.bueno.spi.service.BotServiceProvider;

import java.util.List;
import java.util.Optional;

public class BatataFritaDoBarBot implements BotServiceProvider {


    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        if(intel.getOpponentScore() == 11) return true;

        if(hasZap(intel) && hasCopas(intel)) return true;

        if(hasEspadilha(intel) && hasCopas(intel)) return true;

        if(getNumberOfManilhas(intel) > 1 && getAverageCardValue(intel) > 7 ) return true;

        if(intel.getOpponentScore() > 7) {
            return (getNumberOfManilhas(intel) > 0 && getAverageCardValue(intel) > 7);
        }
        if (intel.getOpponentScore() > 6) {
            return (getAverageCardValue(intel) >= 7);
        }

        return false;
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        int blefe = intel.getOpponentScore() - intel.getScore();

        if(intel.getScore() == 11 || intel.getOpponentScore() == 11) return false;

        if(blefe > 7) return true;

        if(getNumberOfManilhas(intel) > 1) return true;

        if(intel.getOpponentScore() > 7) {
            return (getNumberOfManilhas(intel) > 0 && getAverageCardValue(intel) > 7);
        }
        if (intel.getOpponentScore() > 6) {
            return (getAverageCardValue(intel) >= 7);
        }

        return false;
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        return null;
    }

    GameIntel.RoundResult getlastRoundResult(GameIntel intel){
        return intel.getRoundResults().get(0);
    }

    @Override
    public int getRaiseResponse(GameIntel intel) {
        return 0;
    }
    private CardToPlay chooseCardSecondRound(GameIntel intel){

        if(getlastRoundResult(intel).equals(GameIntel.RoundResult.WON)){

            if (getNumberOfManilhas(intel) == 1) {
                if(hasZap(intel) || hasCopas(intel)) return CardToPlay.of(getHighestNormalCard(intel));
                if(hasOuros(intel) || hasEspadilha(intel)) return CardToPlay.of(getHighestCard(intel));
            }
            if(getNumberOfManilhas(intel) > 1){
                return CardToPlay.discard(getLowestCard(intel));

            }else{
                if(getAverageCardValue(intel) > 8){
                    return CardToPlay.of(getLowestCard(intel));
                }else return CardToPlay.of(getHighestCard(intel));
            }

        }else{
            return CardToPlay.of(getHighestCard(intel));
        }

    }

    private CardToPlay chooseCardThirdRound(GameIntel intel){
        return CardToPlay.of(intel.getCards().get(0));
    }

    int getNumberOfManilhas(GameIntel intel){
        int quantityOfManilhas = 0;
        for (TrucoCard card: intel.getCards())
            if(card.isManilha(intel.getVira()))
                quantityOfManilhas += 1;
        return quantityOfManilhas;
    }


    boolean checkIfIsTheFirstToPlay(GameIntel intel){
        return intel.getOpponentCard().isEmpty();
    }

    public TrucoCard getHighestCard(GameIntel intel) {
        TrucoCard highestCard = intel.getCards().get(0);

        return highestCard;
    }

    public TrucoCard getLowestCard(GameIntel intel) {
        TrucoCard lowestCard = intel.getCards().get(0);

        return lowestCard;
    }

    public TrucoCard getHighestNormalCard(GameIntel intel) {
        TrucoCard lowestNormalCard = intel.getCards().get(0);

        return lowestNormalCard;
    }

    public Optional<TrucoCard> getLowestToWin(GameIntel intel) {
        TrucoCard lowestCard = intel.getCards().get(0);

        return Optional.ofNullable(lowestCard);
    }



    boolean isLastRoundWinner(GameIntel intel){
        return(GameIntel.RoundResult.WON).equals(getlastRoundResult(intel));

    }

    boolean hasZap(GameIntel intel) {
        return false;
    }

    boolean hasCopas(GameIntel intel) {
        return false;
    }

    boolean hasEspadilha(GameIntel intel) {
        return false;
    }

    boolean hasOuros(GameIntel intel) {
        return false;
    }

    public boolean isMaoDeFerro(GameIntel intel) {
        return false;
    }

    public boolean isMaoDeOnze(GameIntel intel){
        return false;
    }

    public int getAverageCardValue(GameIntel build) {
        return 0;
    }
}
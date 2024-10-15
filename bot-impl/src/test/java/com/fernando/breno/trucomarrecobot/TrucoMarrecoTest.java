package com.fernando.breno.trucomarrecobot;

import com.bueno.spi.model.CardRank;
import com.bueno.spi.model.CardSuit;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.bueno.spi.model.CardRank.*;
import static com.bueno.spi.model.CardSuit.*;

import static org.junit.jupiter.api.Assertions.*;

class TrucoMarrecoTest {
    private List<TrucoCard> hand;
    private List<TrucoCard> openCards;
    private List<GameIntel.RoundResult> result;

    private TrucoCard vira;

    private GameIntel intel;

    private GameIntel.StepBuilder stepBuilder;

    TrucoMarreco  trucoMarreco;
    @BeforeEach
    void setUp() {trucoMarreco = new TrucoMarreco();}
    @Nested
    @DisplayName("getMaoDeOnzeResponse")
     class GetMaoDeOnzeResponse {
          @Test
          @DisplayName("Testa se aceita mão de onze  com duas manilhas ou mais")
          void testMaoDeOnzeWithTwoOrMoreManilhas() {
              hand = List.of(TrucoCard.of(SIX, SPADES), TrucoCard.of(SIX, HEARTS), TrucoCard.of(THREE, SPADES));
              vira = TrucoCard.of(FIVE, HEARTS);
              openCards = List.of(vira);

              stepBuilder = GameIntel.StepBuilder.with().gameInfo(List.of(GameIntel.RoundResult.LOST),openCards,vira,1).
                      botInfo(hand,11).opponentScore(5);
              Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
              assertTrue(acceptMaoDeOnze);

          }

        @Test
        @DisplayName("Testa se recusa mão de onze sem manilha")
        void  testHandOfElevenNoManilhasAndNoBiggestCouple() {
            hand = List.of(TrucoCard.of(SIX, SPADES), TrucoCard.of(SIX, HEARTS), TrucoCard.of(THREE, SPADES));
            vira = TrucoCard.of(FOUR, HEARTS);
            openCards = List.of(vira);


            stepBuilder = GameIntel.StepBuilder.with().gameInfo(List.of(GameIntel.RoundResult.DREW),openCards,vira,1).
                    botInfo(hand,11).opponentScore(7);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertFalse(acceptMaoDeOnze);

        }

        @Test
        @DisplayName("Testa se recusa mão de onze com uma  manilha")
        void testMaoDeOnzeOneManilha() {
            hand = List.of(TrucoCard.of(SIX, SPADES), TrucoCard.of(FOUR, HEARTS), TrucoCard.of(FIVE, SPADES));
            vira = TrucoCard.of(FIVE, HEARTS);
            openCards = List.of(vira);


            stepBuilder = GameIntel.StepBuilder.with().gameInfo(List.of(GameIntel.RoundResult.LOST),openCards,vira,1).
                    botInfo(hand,11).opponentScore(8);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertFalse(acceptMaoDeOnze);

        }
        @Test
        @DisplayName("Testa se aceita mão de onze com  uma manilha e um 3")
        void testMaoDeOnzehandStrong() {
            hand = List.of(TrucoCard.of(SIX, SPADES), TrucoCard.of(FOUR, HEARTS), TrucoCard.of(THREE, SPADES));
            vira = TrucoCard.of(FIVE, HEARTS);
            openCards = List.of(vira);


            stepBuilder = GameIntel.StepBuilder.with().gameInfo(List.of(GameIntel.RoundResult.DREW),openCards,vira,1).
                    botInfo(hand,11).opponentScore(5);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertTrue(acceptMaoDeOnze);

        }


        @Test
        @DisplayName("Testa se aceita mão de onze  se ganhou a primeira rodada e tem zap")
        void testMaoDeOnzeWonFirstRoundAndHasZap() {
            hand = List.of(TrucoCard.of(SIX, CLUBS), TrucoCard.of(FOUR, HEARTS), TrucoCard.of(THREE, SPADES));
            vira = TrucoCard.of(FIVE, HEARTS);
            openCards = List.of(vira);


            stepBuilder = GameIntel.StepBuilder.with().gameInfo(List.of(GameIntel.RoundResult.WON),openCards,vira,1).
                    botInfo(hand,11).opponentScore(5);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertTrue(acceptMaoDeOnze);

        }

        @Test
        @DisplayName("Testa se recusa mão de onze com uma manilha e carta alta, mas considerada fraca")
        void testMaoDeOnzeWithOneManilhaAndHighCard() {
            hand = List.of(TrucoCard.of(FIVE, SPADES), TrucoCard.of(FOUR, HEARTS), TrucoCard.of(QUEEN, DIAMONDS));
            vira = TrucoCard.of(FIVE, HEARTS);
            openCards = List.of(vira);


            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.DREW), openCards, vira, 1)
                    .botInfo(hand, 11)
                    .opponentScore(6);
            Boolean refusalMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertFalse(refusalMaoDeOnze);
        }

        @Test
        @DisplayName(" Teste se ambos estão na mão de onze")
        void testBothInTheHandOfEleven() {
            hand = List.of(TrucoCard.of(FIVE, HEARTS), TrucoCard.of(FIVE, CLUBS), TrucoCard.of(THREE, CLUBS));
            vira = TrucoCard.of(JACK, HEARTS);
            openCards = List.of();
            result =List.of();

            stepBuilder = GameIntel.StepBuilder.with().gameInfo(result,openCards, vira, 1).
                    botInfo(hand, 11).opponentScore(11);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertTrue(acceptMaoDeOnze);
        }
        @Test
        @DisplayName(" teste mao de 3A ")
        void testHandOfThreeestou() {
            hand = List.of(TrucoCard.of(THREE, HEARTS), TrucoCard.of(THREE, CLUBS), TrucoCard.of(THREE, CLUBS));
            vira = TrucoCard.of(JACK, HEARTS);
            openCards = List.of();
            result =List.of();

            stepBuilder = GameIntel.StepBuilder.with().gameInfo(result,openCards, vira, 1).
                    botInfo(hand, 11).opponentScore(9);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertTrue(acceptMaoDeOnze);
        }

        @Test
        @DisplayName("Testa se a mão de onze é aceita quando as cartas possuem um valor superior às do oponente")
        void TestMaoDeOnzeRankOpponentLow (){
            hand = List.of(TrucoCard.of(THREE, HEARTS), TrucoCard.of(THREE, CLUBS), TrucoCard.of(THREE, CLUBS));
            vira = TrucoCard.of(JACK, HEARTS);
            openCards = List.of();
            result =List.of();
            stepBuilder = GameIntel.StepBuilder.with().gameInfo(result,openCards, vira, 1).
                    botInfo(hand, 11).opponentScore(5);
            Boolean acceptMaoDeOnze = trucoMarreco.getMaoDeOnzeResponse(stepBuilder.build());
            assertTrue(acceptMaoDeOnze);
        }

     }






    @Nested
    @DisplayName("decideIfRaises")
    class DecideIfRaises {

        @Test
        @DisplayName("Recusa aumento com mão fraca")
        void testDecideIfRaisesWithWeakHand() {
            hand = List.of(TrucoCard.of(THREE, SPADES), TrucoCard.of(THREE, HEARTS), TrucoCard.of(THREE, DIAMONDS));
            vira = TrucoCard.of(FOUR, HEARTS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.LOST), openCards, vira, 1)
                    .botInfo(hand, 9).opponentScore(7);
            assertFalse(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Aceita aumento após ganhar rodada")
        void testDecideIfRaisesAfterWinningRound() {
            hand = List.of(TrucoCard.of(FOUR, SPADES), TrucoCard.of(THREE, HEARTS), TrucoCard.of(FIVE, DIAMONDS));
            vira = TrucoCard.of(FOUR, DIAMONDS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.WON), openCards, vira, 1)
                    .botInfo(hand, 11).opponentScore(5);
            assertTrue(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Recusa aumento após perder rodada")
        void testDecideIfRaisesAfterLosingRound() {
            hand = List.of(TrucoCard.of(THREE, SPADES), TrucoCard.of(THREE, HEARTS), TrucoCard.of(THREE, DIAMONDS));
            vira = TrucoCard.of(FOUR, HEARTS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.LOST), openCards, vira, 1)
                    .botInfo(hand, 9).opponentScore(8);
            assertFalse(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Aceita aumento com maior dupla")
        void testDecideIfRaisesWithHighestPair() {
            hand = List.of(TrucoCard.of(FIVE, SPADES), TrucoCard.of(FIVE, HEARTS));
            vira = TrucoCard.of(FOUR, DIAMONDS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.DREW), openCards, vira, 1)
                    .botInfo(hand, 10).opponentScore(6);
            assertTrue(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Recusa aumento se oponente está ganhando")
        void testDecideIfRaisesWhenOpponentWinning() {
            hand = List.of(TrucoCard.of(THREE, SPADES), TrucoCard.of(THREE, HEARTS), TrucoCard.of(THREE, DIAMONDS));
            vira = TrucoCard.of(FOUR, HEARTS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.LOST), openCards, vira, 1)
                    .botInfo(hand, 8).opponentScore(12);
            assertFalse(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Aceita aumento se oponente tem mão fraca")
        void testDecideIfRaisesWhenOpponentHasWeakHand() {
            hand = List.of(TrucoCard.of(SIX, SPADES), TrucoCard.of(SIX, HEARTS), TrucoCard.of(THREE, SPADES));
            vira = TrucoCard.of(FOUR, HEARTS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.WON), openCards, vira, 1)
                    .botInfo(hand, 11).opponentScore(5);
            assertTrue(trucoMarreco.decideIfRaises(stepBuilder.build()));
        }

        @Test
        @DisplayName("Recusa aumento se a mão não é boa o suficiente")
        void testDecideIfRaisesWhenHandIsNotGood() {
            hand = List.of(TrucoCard.of(THREE, SPADES), TrucoCard.of(TWO, HEARTS), TrucoCard.of(ACE, DIAMONDS));
            vira = TrucoCard.of(FOUR, DIAMONDS);
            openCards = List.of(vira);
            stepBuilder = GameIntel.StepBuilder.with()
                    .gameInfo(List.of(GameIntel.RoundResult.DREW), openCards, vira, 1)
                    .botInfo(hand, 6).opponentScore(10);
        }
    }


}
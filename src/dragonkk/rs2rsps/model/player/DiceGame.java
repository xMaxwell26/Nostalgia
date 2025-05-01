package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.util.Misc;

import java.util.Random;

//Converted from 562

public class DiceGame {

    public static void rollDice(Player player) {
        if (player.hackdice) {  //Dice will always be 90+ :D
            int LOWEST = 90;
            int HIGHEST = 100;
            Random r = new Random();
            generateRolledNumber(LOWEST, HIGHEST, r, player);
            return;
        }
        int LOWEST = 1;
        int HIGHEST = 100;
        Random r = new Random();
        generateRolledNumber(LOWEST, HIGHEST, r, player);
    }

    private static void generateRolledNumber(int lowest, int highest, Random r, Player player) {
        if (lowest > highest) {
            return;
        }
        long range = (long) highest - (long) lowest + 1;
        long fraction = (long) (range * r.nextDouble());
        int numberRolled = (int) (fraction + lowest);
        sendNumber(player, numberRolled);

    }

    private static void sendNumber(Player player, int numberRolled) {
        if (player.dicewait > 0) {
            player.getFrames().sendChatMessage(0, "Please wait 10 seconds to re-roll the dice.");
            return;
        }
        if (player.hackdice) {
            player.animate(11900);
            player.graphics(2075);//2072 for red dice 2,6
            player.getMask().setLastChatMessage(new ChatMessage(0, 0, "[DICE ROLL]" + Misc.formatPlayerNameForDisplay(player.getUsername()) + " rolled " + numberRolled + " " + "on the dice!"));
            player.getMask().setChatUpdate(true);
            player.getFrames().sendChatMessage(0, "<col=FF0000>You</col> rolled <col=FF00FF>" + numberRolled + "</col> " + "on the dice");
            player.dicewait = 18;
            return;
        }
        player.animate(11900);
        player.graphics(2075);
        player.getMask().setLastChatMessage(new ChatMessage(0, 0, "[DICE ROLL]" + Misc.formatPlayerNameForDisplay(player.getUsername()) + " rolled " + numberRolled + " " + "on the dice!"));
        player.getMask().setChatUpdate(true);
        player.getFrames().sendChatMessage(0, "<col=FF0000>You</col> rolled <col=FF00FF>" + numberRolled + "</col> " + "on the dice");
        player.dicewait = 18;
    }
}
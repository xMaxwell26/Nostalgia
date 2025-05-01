package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.forums.DatabaseFunctions;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

import java.util.GregorianCalendar;

import static dragonkk.rs2rsps.util.Serializer.appendData;

//import sun.util.calendar.Gregorian;

public class Claim implements Command {

    public int items;

    public void execute(String[] args, Player p) {
        String name = p.getUsername().replace("_", " ").toLowerCase();
        if (!p.getCombat().isSafe(p)) {
            p.getFrames().sendChatMessage(0, "You can't use this command here.");
            return;
        }
        if (Server.voteDisabled) {
            p.getFrames().sendChatMessage(0, "The vote system is currently offline, an admin must re-enable it.");
            p.getFrames().sendChatMessage(0, "Please wait and try again later.");
            return;
        }
        if (p.votedisabled == 1) {
            p.getFrames().sendChatMessage(0, "You can't claim anymore votes for abusing the vote system.");
            return;
        }
        if (p.voted > 1) {
            p.getFrames().sendChatMessage(0, "Please do not proxy vote otherwise you will be banned " + name + ".");
        }
        if (p.votedcount > 3) {
            p.votedisabled = 1;
            p.getFrames().sendChatMessage(0, "Your vote has been disabled for abusing it.");
            return;
        }
        if (p.getInventory().getFreeSlots() <= 3) {
            p.getFrames().sendChatMessage(0, "You must have at least 4 spaces free to claim your vote item/items.");
            return;
        }
        if (DatabaseFunctions.checkVotes(p, name)) {
            if (!p.getConnection().getChannel().getRemoteAddress().toString().startsWith("/" + p.voteip + "")) {
                p.getFrames().sendChatMessage(0, "You have not voted with this ip you are currently logged in with.");
                p.getFrames().sendChatMessage(0, "Your last ip that you voted on starts with " + p.voteip + ".");
                return;
            }
            if (Math.random() * 100 >= 95) {
                p.getInventory().addItem(15071, 1);
                items = 15071;
                p.getFrames().sendChatMessage(0, "<col=00ff00><shad=ff0000>Congratz [" + name + "] on getting this rare item (5% Chance of getting Per vote).");
            }
            if (Math.random() * 100 >= 99) {
                p.getInventory().addItem(15069, 1);
                items = 15069;
                p.getFrames().sendChatMessage(0, "<col=00ff00><shad=ff0000>Congratz [" + name + "] on getting a SUPER RARE item (1% Chance of getting Per vote).");
            }
            if (p.voteitem == 1) {
                if (Math.random() * 100 >= 0 && Math.random() * 100 <= 25) {
                    p.getInventory().addItem(11694, 1);
                    items = 11694;
                    p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Armadyl Godsword has been added.");
                    p.getFrames().sendChatMessage(0, "Armadyl Godsword is located in your bank.");
                } else {
                    if (Math.random() * 100 >= 25 && Math.random() * 100 <= 50) {
                        p.getInventory().addItem(11696, 1);
                        items = 11696;
                        p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Bandos Godsword has been added.");
                        p.getFrames().sendChatMessage(0, "Bandos Godsword is located in your bank.");
                    } else {
                        if (Math.random() * 100 >= 50 && Math.random() * 100 <= 75) {
                            p.getInventory().addItem(11698, 1);
                            items = 11698;
                            p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Saradomin Godsword has been added.");
                            p.getFrames().sendChatMessage(0, "Saradomin Godsword is located in your bank.");
                        } else {
                            if (Math.random() * 100 >= 75 && Math.random() * 100 <= 100) {
                                p.getInventory().addItem(11700, 1);
                                items = 11700;
                                p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Zamorak Godsword has been added.");
                                p.getFrames().sendChatMessage(0, "Zamorak Godsword is located in your bank.");
                            } else {
                                p.getInventory().addItem(11698, 1);
                                items = 11698;
                                p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Saradomin Godsword has been added.");
                                p.getFrames().sendChatMessage(0, "Saradomin Godsword is located in your bank.");
                            }
                        }
                    }
                }
            } else {
                if (p.voteitem == 2) {
                    p.getInventory().addItem(14484, 1);
                    items = 14484;
                    p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Dragon Claws has been added.");
                    p.getFrames().sendChatMessage(0, "Dragon Claws is located in your bank.");
                } else {
                    if (p.voteitem == 3) {
                        if (Math.random() * 100 >= 0 && Math.random() * 100 <= 35) {
                            p.getInventory().addItem(13744, 1);
                            items = 13744;
                            p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Spectral Spirit Shield has been added.");
                            p.getFrames().sendChatMessage(0, "Spectral Spirit Shield is located in your bank.");
                        } else {
                            if (Math.random() * 100 >= 35 && Math.random() * 100 <= 43) {
                                p.getInventory().addItem(13736, 1);
                                items = 13736;
                                p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Blessed Spirit Shield has been added.");
                                p.getFrames().sendChatMessage(0, "Blessed Spirit Shield is located in your bank.");
                            } else {
                                if (Math.random() * 100 >= 50 && Math.random() * 100 <= 80) {
                                    p.getInventory().addItem(13738, 1);
                                    items = 13738;
                                    p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Arcane Spirit Shield has been added.");
                                    p.getFrames().sendChatMessage(0, "Arcane Spirit Shield is located in your bank.");
                                } else {
                                    if (Math.random() * 100 >= 80 && Math.random() * 100 <= 90) {
                                        p.getInventory().addItem(13742, 1);
                                        items = 13742;
                                        p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Elysian Spirit Shield has been added.");
                                        p.getFrames().sendChatMessage(0, "Elysian Spirit Shield is located in your bank.");
                                    } else {
                                        if (Math.random() * 100 >= 95 && Math.random() * 100 <= 100) {
                                            p.getInventory().addItem(13740, 1);
                                            items = 13740;
                                            p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Divine Spirit Shield has been added.");
                                            p.getFrames().sendChatMessage(0, "Divine Spirit Shield is located in your bank.");
                                        } else {
                                            p.getInventory().addItem(13744, 1);
                                            items = 13744;
                                            p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Spectral Spirit Shield has been added.");
                                            p.getFrames().sendChatMessage(0, "Spectral Spirit Shield is located in your bank.");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (p.voteitem == 4) {
                            p.getInventory().addItem(18348, 1);
                            items = 18348;
                            p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + Dung Exp Lamp has been added.");
                            p.getFrames().sendChatMessage(0, "Dung Exp Lamp is located in your bank.");
                            p.getFrames().sendChatMessage(0, "<col=B40404>Drop the lamps to gain exp!");
                        } else {
                            if (p.voteitem == 5) {
                                items = 79;
                                p.Points += 100;
                                p.getFrames().sendChatMessage(0, "Thanks for voting, vote points + 100 PKP has been added.");
                            } else {
                                items = 79;
                                p.getFrames().sendChatMessage(0, "There was an error processing your vote.");
                                p.getFrames().sendChatMessage(0, "Please try and vote again in the next 24hours.");
                            }
                        }
                    }
                }
            }
            for (Player d : World.getPlayers()) {
                if (d == null)
                    continue;
                //d.getFrames().sendChatMessage(0, "<col=B40404><shad=4000FF>Thank you for voting [" + name + "] at www.Nostalgia.org/vote"); // TODO
                if (items == 79) {
                    d.getFrames().sendChatMessage(0, "<col=B40404><shad=4000FF>[" + name + "] has received 100 PKP and 10 vote points!");
                } else {
                    d.getFrames().sendChatMessage(0, "<col=B40404><shad=4000FF>[" + name + "] has received a " + ItemDefinitions.forID(items).name + " and 10 vote points!");
                }
                d.getFrames().sendChatMessage(0, "<col=ffffff><shad=B40404>[" + name + "] receives an additional 15mil for voting.");
            }
            p.votePoints += 10;
            p.getInventory().addItem(995, 15000000);
            p.voted++;
            p.votedcount++;
            p.voteTotal++;
            appendData("logs/votelog/" + p.getUsername() + ".txt", "Time: " + new GregorianCalendar().getTime() + ".");
            appendData("logs/votelog/" + p.getUsername() + ".txt", "Timezone: " + new GregorianCalendar().getTimeZone() + "");
            appendData("logs/votelog/" + p.getUsername() + ".txt", "Change: " + new GregorianCalendar().getGregorianChange() + "");
            appendData("logs/votelog/" + p.getUsername() + ".txt", "IP: " + p.getConnection().getChannel().getRemoteAddress() + "");
            p.animate(802);
            if (p.voteTotal == 1) {
                p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have voted for the first time on Nostalgia!")); //TODO
                p.getMask().setChatUpdate(true);
            } else {
                p.getMask().setLastChatMessage(new ChatMessage(0, 0, "I have voted " + p.voteTotal + " times for Nostalgia!"));
                p.getMask().setChatUpdate(true);
            }
        } else {
            p.getFrames().sendChatMessage(0, "You have not voted today or you have already voted.");
            p.getFrames().sendChatMessage(0, "Remember to <col=B40404>not</col> use _ (underscores) when you vote, use spaces.");
        }
    }
}
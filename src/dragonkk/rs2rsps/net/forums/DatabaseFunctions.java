package dragonkk.rs2rsps.net.forums;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.MYSQL;
import dragonkk.rs2rsps.util.Serializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import static dragonkk.rs2rsps.util.Serializer.appendData;


public class DatabaseFunctions {

    private static MYSQL getDatabase() {
        if (Server.database != null)
            return Server.database;
        return null;
    }

    	/*public static boolean offline() {
        try {
			getDatabase().updateQuery("DELETE FROM `online` WHERE id = 1;");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean online() {
		try {
			getDatabase().updateQuery("INSERT INTO `online` (id, currentlyonline) VALUES('1','"+ World.getPlayers().size()+"');");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}    */

   /*public static boolean checkStatus(Player player) {
        try {
        ResultSet results = getDatabase().getQuery("SELECT * FROM `status` WHERE `username` = '" + player.getUsername() + "' AND `given` = '0' LIMIT 10;");
			while(results.next()) {
            player.getBank().addItem(results.getInt("item"), results.getInt("quantity"));
            getDatabase().updateQuery("UPDATE `status` SET `given` = '10' WHERE `id`='" + results.getInt("id") + "';");
                    return true;
                }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/


    public static boolean addDonateItems(final Player p, final String name) {
        try {
            if (Server.voteDisabled) {
                System.out.println("Aborted connection.");
                return true;
            }
            if (p == null) {
                p.getFrames().sendChatMessage(0, "Error with account, please try again later.");
                return false;
            }
            String username = name.replaceAll(" ", "_");
            ResultSet results = getDatabase().getQuery("SELECT * FROM donation WHERE username = '" + username + "'");
            boolean b = false;
            while (results.next()) {
                int prod = Integer.parseInt(results.getString("productid"));
                int price = Integer.parseInt(results.getString("price"));
                if (prod == 1 && price == 5) {
                    p.isDonator = true;
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "You are now a Regular Donator!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received normal donator at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 2 && price == 15) {
                    p.isDonator = true;
                    p.extremeDonator = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getBank().bank.add(new Item(13740));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "You are now an Extreme Donator & received a free divine!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received extreme donator at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 3 && price == 25) {
                    p.isDonator = true;
                    p.extremeDonator = true;
                    p.trusted = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getBank().bank.add(new Item(13740));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "You are now Trusted & an Extreme Donator & recieved a free divine!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received trusted + extreme at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 4 && price == 50) {
                    p.isDonator = true;
                    p.extremeDonator = true;
                    p.superextremeDonator = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "You are now a Super Extreme Donator!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received super extreme donator at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 5 && price == 7) {
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13740));
                    p.getBank().bank.add(new Item(13742));
                    p.getBank().bank.add(new Item(14484));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "10x Divine Spirit Shield + 1x Elysain Spirit Shield, 1x Dragon Claws");
                    p.getFrames().sendChatMessage(0, "has been added to your bank (Max Cash)!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received max cash at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 6 && price == 14) {
                    p.getBank().bank.add(new Item(1044));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Green Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received green partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 7 && price == 19) {
                    p.getBank().bank.add(new Item(1040));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Yellow Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received yellow partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 8 && price == 24) {
                    p.getBank().bank.add(new Item(1042));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Blue Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received blue partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 9 && price == 28) {
                    p.getBank().bank.add(new Item(1038));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Red Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received red partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 10 && price == 32) {
                    p.getBank().bank.add(new Item(1048));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x White Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received white partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 11 && price == 43) {
                    p.getBank().bank.add(new Item(1046));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Purple Partyhat has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received purple partyhat at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 12 && price == 40) {
                    p.getBank().bank.add(new Item(962));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Christmas Cracker has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received christmas cracker at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 13 && price == 38) {
                    p.getBank().bank.add(new Item(1037));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Bunny Ears has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received bunny ears at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 14 && price == 110) {
                    p.getBank().bank.add(new Item(1038));
                    p.getBank().bank.add(new Item(1040));
                    p.getBank().bank.add(new Item(1042));
                    p.getBank().bank.add(new Item(1044));
                    p.getBank().bank.add(new Item(1046));
                    p.getBank().bank.add(new Item(1048));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Partyhat Set has been added to your bank!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received party hat set at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 15 && price == 130) {
                    p.isDonator = true;
                    p.extremeDonator = true;
                    p.superextremeDonator = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getBank().bank.add(new Item(1038));
                    p.getBank().bank.add(new Item(1040));
                    p.getBank().bank.add(new Item(1042));
                    p.getBank().bank.add(new Item(1044));
                    p.getBank().bank.add(new Item(1046));
                    p.getBank().bank.add(new Item(1048));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "1x Partyhat Set has been added to your bank & Super Donator rank is active!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received partyhat set + super extreme donator at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 16 && price == 10) {
                    p.noREQ = true;
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "No item req has been activated!");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received no item req at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 17 && price == 5) {
                    p.Points += 750;
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "750 shop points has been added to your account.");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received points at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 18 && price == 5) {
                    p.Skull = true;
                    p.getMask().setApperanceUpdate(true);
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "Red skull is now activated.");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received red skull at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 19 && price == 15) {
                    p.DoublePoints = true;
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "Double points per kill has now been activated.");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received points at: " + new GregorianCalendar().getTime());
                    b = true;
                } else if (prod == 20 && price == 4) {
                    p.getBank().bank.add(new Item(7927));
                    p.getFrames().sendChatMessage(0, "Thanks for donating.");
                    p.getFrames().sendChatMessage(0, "Easter ring has now been added to your bank.");
                    Serializer.SaveAccount(p);
                    Serializer.BackupAccount(p);
                    Serializer.Backup2Account(p);
                    appendData("logs/donations/" + p.getUsername() + ".txt", p.getUsername() + " has received easter ring at: " + new GregorianCalendar().getTime());
                    b = true;
                } else {
                    //p.Jailed = true;
                    p.getFrames().sendChatMessage(0, "Nice try.");
                    //p.getMask().getRegion().teleport(2167, -20902, 0, 0);
                }

            }
            if (b) {
                getDatabase().updateQuery("DELETE FROM `donation` WHERE `username` = '" + username + "';");
                return true;
            } else {
                if (p.loginmessage) {
                    p.getFrames().sendChatMessage(0, "Error with your donation, please try again later.");
                    p.getFrames().sendChatMessage(0, "If you still can't claim it later, post on the forums.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkVotes(Player p, String playerName) {
        try {
            if (Server.voteDisabled) {
                System.out.println("Aborted conncetion.");
                return true;
            }
            ResultSet results = getDatabase().getQuery("SELECT * FROM Votes WHERE username = '" + playerName + "'");
            while (results.next()) {
                int recieved = results.getInt("recieved");
                p.voteitem = results.getInt("item");
                p.voteip = results.getInt("ip");
                if (recieved == 0) {
                    getDatabase().updateQuery("UPDATE Votes SET recieved = 1 WHERE username = '" + playerName + "'");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
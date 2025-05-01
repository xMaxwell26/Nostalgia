package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.*;
import dragonkk.rs2rsps.model.Hits.Hit;
import dragonkk.rs2rsps.model.Hits.HitType;
import dragonkk.rs2rsps.net.Frames;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.net.forums.DatabaseFunctions;
import dragonkk.rs2rsps.skills.prayer.Prayer;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;
import dragonkk.rs2rsps.util.Serializer;

import java.io.Serializable;
import java.util.*;

//import dragonkk.rs2rsps.model.player.clan.ClanSettings;


public class Player extends Entity implements Serializable {

    public transient int slot, inputId, itemID;
    public int displaystatus = 0;
    public int TomeTimer = 0;
    public boolean botstop = false;
    public int renderEmote = 0;
    public int tabbing = 0;
    public int teleblockDelay = 0;
    public int teleblockimmuneDelay = 0;
    public int magicresist = 0;
    public int curseDelay = 0;
    public boolean allowed = false;
    public boolean resetShard = false;
    public int bonusmagicdmg = 0;
    public String colour = "00FF00";
    public String onlinestatus = "Online";
    public int warnings = 0;
    public int levelset = 138;
    public int WarningTeleport = 0;
    public int WildTeleport = 0;
    public int skillset = 0;
    public int overload = 0;
    public int AutoCastSpell = 0;
    public int CanPm = 0;
    public byte ohair = 0;
    public byte otorso = 0;
    public byte olegs = 0;
    public byte oarms = 0;
    public byte obeard = 0;
    public byte c1 = 0;
    int stanceEmote = 0;
    public byte c2 = 0;
    public byte c3 = 0;
    public byte c4 = 0;
    public byte c5 = 0;
    public boolean isForumMod = false;
    public boolean AutoCast = false;
    public boolean loginmessage = false;
    public int overloadstats = 0;
    public int votedcount = 0;
    public int explosions = 0;
    public boolean hackdice = false;
    public boolean DoublePoints = false;
    public boolean Skull = false;
    public boolean isNpc = false;
    public boolean isMorphed = false;
    public int voteitem = 0;
    public boolean hasChangedname = false;
    public boolean canChangename = false;
    public String target;
    public String badperson = "";
    public int refresh = 0;
    public int secondscreen = 0;
    transient String requested = "";
    public int BankPinNumber = 0;
    public boolean needConfirm = false;
    public boolean BankConfirm = false;
    public boolean entered = false;
    public boolean hasPin = false;
    public int yellTimer = 0;
    public int privateChatMode;
    public boolean yellremoved = false;
    public boolean addingFriend = false;
    public boolean in2Delay = false;

    public void rest() {
        if (!isResting) animate(11786);
        else animate(11788);
        isResting = !isResting;
    }

    /*
    * Admin CP
    * By Jet Kai
    * 25/11/2011
     */

    public int SkullOn = 0;
    public int hiddenadmin = 0;
    public boolean Skullon = true;

    /*
    * End Of AdminCP Options.
     */
    public int voteip = 0;
    public int totalcommands = 0;
    public int page = 0;
    int Removetimer = 0;
    public int unsafeKills = 0;
    long lastDFS;
    public int DFShit = 0;
    public boolean DFSSpecial;
    public boolean notattackable = false;
    public int votedisabled = 0;
    public int voteTotal = 0;
    public int voted = 0;
    private int toggle = 0;
    public int deathemote = 0;
    public int votePoints = 0;
    int doublePoints2 = 0;
    public int xpGained = 0;
    private int glow = 0;
    private int currentTab = 0;
    public int commandwait = 0;
    public boolean noREQ = false;
    public boolean sure = false;
    public boolean SolidGold = false;
    public boolean isResting = false;
    public boolean showwelcome = true;
    double epAmount = 50.6;
    public int infSpec = 0;
    public TradeSession currentTradeSession;
    private static final long serialVersionUID = -393308022192269041L;
    public boolean playerMuted = false;
    public boolean trusted = false;
    private boolean banReset2 = false;
    private boolean specRestore = false;
    public int spellbook = 0;
    public int Kills2;
    public int Points;
    private Player tradePartner;
    public String trader = "";
    public int Deaths2;
    int dicewait = 0;
    public int meleePicked = 0;
    public boolean Jailed = false;
    public int magicPicked = 0;
    public int rangedPicked = 0;
    public int meleeDelay = 0;
    public int rangedDelay = 0;
    public int toyDelay = 0;
    public int magicDelay = 0;
    public int removeStarter = 0;
    public boolean isDonator = false;
    public boolean extremeDonator = false;
    public boolean superextremeDonator = false;
    private int saveTimer = 0;
    public boolean AutoRetaliate;
    public String skulledOn = "";
    public int LogoutDelay = 0;
    public int skullTimer = 0;
    public boolean didRequestTrade = false;
    private int tickTab = 0;
    public long lastResponce;
    public int pickupDelay;
    private transient ShopHandler shophandler;

    private int specTimer = 0;

    private void AutomaticGlow() {
        if (this.getEquipment().contains(15069)) graphics(246);
        if (this.getEquipment().contains(15071)) graphics(247);
    }

    private void TaskTab() {
        String status = null;
        if (rights == 2) status = "<shad=cc0ff><col=9900CC>Administrator";
        else if (rights == 1 && !isForumMod) status = "<col=BDBDBD>Moderator";
        else if (isForumMod) status = "<col=BDBDBD>Forum Mod";
        else if (rights == 0 && !isDonator) status = "<col=ffffff>Player";
        if (isDonator && !extremeDonator && rights == 0 && !trusted && !isForumMod)
            status = "<col=ff0000><shad=ffffff>Donator";
        else if (trusted && rights == 0 && !isForumMod) status = "<col=8904B1><shad=ffffff>Trusted";
        else if (extremeDonator && !superextremeDonator && rights == 0 && !trusted && !isForumMod)
            status = "<col=00FF00><shad=ffffff>Extreme Donator";
        else if (superextremeDonator && rights == 0 && !isForumMod)
            status = "<col=FFFFFF><shad=C58917>Super Donator";
        this.getFrames().sendString("Nostalgia 614", 259, 11);
        this.getFrames().sendString("Players online: " + World.getPlayers().size() + "", 259, 1);
        //this.getFrames().sendString("   <col=01DF01>Server ping:<col=0174DF> "+ Server.tickTimer+"", 259, 2);
        this.getFrames().sendString("    <col=01DF01>Warnings:<col=0174DF> " + warnings + "", 259, 2);
        this.getFrames().sendString(" <col=01DF01>Status: " + status + "", 259, 3);
        this.getFrames().sendString("   <col=01DF01>Total kills:<col=0174DF> " + Kills2 + "", 259, 30);
        this.getFrames().sendString("<col=01DF01>Unsafe kills:<col=0174DF> " + unsafeKills + "", 259, 31);
        this.getFrames().sendString("<col=01DF01>Deaths:<col=0174DF> " + Deaths2 + "", 259, 32);
        this.getFrames().sendString("<col=01DF01>Shop points:<col=0174DF> " + Points + "", 259, 33);
        //this.getFrames().sendString("<col=01DF01>Current time: <col=0174DF>"+new GregorianCalendar().getTime().getHours()+":"+new GregorianCalendar().getTime().getMinutes()+":"+new GregorianCalendar().getTime().getSeconds()+"", 259, 4);
        this.getFrames().sendString("<col=01DF01>Uptime: " + World.hours + "h:" + World.minutes + "m:" + World.seconds + "s", 259, 4);
        this.getFrames().sendString("Kdr/Other", 259, 6);
        if (this.hasPin && this.BankPinNumber > 0)
            this.getFrames().sendString("<col=01DF01>Bank Status:<col=0174DF> Secure", 259, 5);
        else this.getFrames().sendString("<col=01DF01>Bank Status:<col=0174DF> No Pin", 259, 5);
        //this.getFrames().sendString("<col=01DF01>Clans:<col=0174DF> "+World.getClanManager().clantotal+"", 259, 33);
        this.getFrames().sendString("   <col=01DF01>Total commands:<col=0174DF> " + totalcommands + "", 259, 22);
        this.getFrames().sendString("<col=01DF01>Total votes:<col=0174DF> " + voteTotal + "", 259, 23);
        this.getFrames().sendString("<col=01DF01>Vote points:<col=0174DF> " + this.votePoints + "", 259, 24);
    }

    public void tick() {
	/*
	* Overload
	* Potions
	*/
        if (overload > 0) overload--;
        if (overload == 9) {
            animate(3170);
            hit(100);
        }
        if (overload == 7) {
            animate(3170);
            hit(100);
        }
        if (overload == 5) {
            animate(3170);
            hit(100);
        }
        if (overload == 3) {
            animate(3170);
            hit(100);
        }
        if (overload == 1) {
            animate(3170);
            hit(100);
        }
        if (overloadstats > 0) overloadstats--;
        if (overloadstats > 0) {
            getSkills().set(Skills.STRENGTH, getSkills().getLevelForXp(Skills.STRENGTH) + 26);
            getSkills().set(Skills.ATTACK, getSkills().getLevelForXp(Skills.ATTACK) + 26);
            getSkills().set(Skills.DEFENCE, getSkills().getLevelForXp(Skills.DEFENCE) + 26);
            getSkills().set(Skills.RANGE, getSkills().getLevelForXp(Skills.RANGE) + 23);
            getSkills().set(Skills.MAGIC, getSkills().getLevelForXp(Skills.MAGIC) + 7);
        }
        if (overloadstats == 1) getFrames().sendChatMessage(0, "The effects of the overload potion has worn off.");
	/*
	* End of Overload
	* Potions
	*/
        if (explosions > 0) explosions--;
        if (secondscreen > 0) secondscreen--;
        if (tickTab > 0) tickTab--;
        if (curseDelay > 0) curseDelay--;
        if (teleblockDelay > 0) teleblockDelay--;
        if (teleblockimmuneDelay > 0) teleblockimmuneDelay--;
        if (curseDelay == 1) {
            getFrames().sendClickableInterface(778);
            getFrames().sendChatMessage(0, "The curse of the Vesta's Spear's special has worn off.");
        }
        if (tabbing > 0) tabbing--;
        if (yellTimer > 0) yellTimer--;
        if (tickTab == 0) {
            TaskTab();
            tickTab = 2;
        }
        if (saveTimer > 0) saveTimer--;
        if (glow > 0) glow--;
        if (glow == 0) {
            AutomaticGlow();
            glow = 3;
        }
        if (DFShit > 0) DFShit--;
        if (TomeTimer > 0) TomeTimer--;
        if (saveTimer == 0) {
            if (this == null || this.getConnection().isDisconnected() || Server.updateTime > 3) return;
            Serializer.SaveAccount(this);
            saveTimer = 90;
        }
        if (pickupDelay > 0) pickupDelay--;
        if (toyDelay > 0) toyDelay--;
        if (dicewait > 0) dicewait--;
        if (Removetimer > 0) Removetimer--;
        if (commandwait > 0) commandwait--;
        if (this.getCombatDefinitions().specpercentage < 100) {
            specTimer++;
            if (specTimer == 60) {
                this.getCombatDefinitions().specpercentage += 10;
                this.getCombatDefinitions().refreshSpecial();
                specTimer = 0;
            }
        }
        if (this.getCombatDefinitions().specpercentage > 100) {
            this.getCombatDefinitions().specpercentage = 100;
            this.getCombatDefinitions().refreshSpecial();
        }
    }


    //Main Information Start
    private int combatDelay;
    private boolean isAttacking;
    private Entity attackingEntity;
    private String Username;
    private String DisplayName;
    private String Password;
    private Calendar BirthDate;
    private Calendar RegistDate;
    private short Country;
    private String Email;
    private byte Settings;
    @SuppressWarnings("unused")
    private boolean isMuted;
    private boolean isBanned;
    private boolean isLOCKED;
    private Date Membership;
    private List<String> friends;
    private transient List<String> ignores;
    private List<String> Messages;
    public int LastIp;
    private int skillsReset2;
    private byte rights;

    //Saving classes here
    private Appearence appearence;
    private Inventory inventory;
    private Equipment equipment;
    private Skills skills;
    private Banking bank;
    private CombatDefinitions combatdefinitions;
    private Prayer prayer;
    private MusicManager musicmanager;

    private transient ConnectionHandler connection;
    private transient Frames frames;
    private transient Mask mask;
    private transient Gpi gpi;
    private transient Gni gni;
    private transient Queue<Hit> queuedHits;
    private transient Hits hits;
    private transient InterfaceManager intermanager;
    private transient HintIconManager hinticonmanager;
    private transient MinigameManager Minigamemanager;
    private transient Dialogue dialogue;
    private transient boolean isOnline;
    private transient boolean inClient;


    public Player(String Username, String Password, Calendar Birth, Calendar ThisDate, short Country, String Email, byte Settings) {
        this.isResting = false;
        this.setUsername(Username);
        this.setDisplayName(Username);
        this.setPassword(Password);
        this.setCurrentTab(10);
        this.setBirthDate(BirthDate);
        this.setRegistDate(ThisDate);
        this.setCountry(Country);
        this.setEmail(Email);
        this.setSettings(Settings);
        this.setMuted(false);
        this.setBanned(false);
        this.setLOCKED(false);
        this.setMembership(new Date());
        this.setFriends(new ArrayList<String>(200));
        this.setIgnores(new ArrayList<String>(100));
        this.setMessages(new ArrayList<String>());
        if (Math.random() * 100 >= 0 && Math.random() * 100 <= 25)
            this.setLocation(RSTile.createRSTile(3183, 3445, (byte) 0));
        else if (Math.random() * 100 >= 25 && Math.random() * 100 <= 50)
            this.setLocation(RSTile.createRSTile(3187, 3435, (byte) 0));
        else if (Math.random() * 100 >= 50 && Math.random() * 100 <= 75)
            this.setLocation(RSTile.createRSTile(3187, 3443, (byte) 0));
        else if (Math.random() * 100 >= 75 && Math.random() * 100 <= 100)
            this.setLocation(RSTile.createRSTile(3184, 3443, (byte) 0));
        else this.setLocation(RSTile.createRSTile(3183, 3445, (byte) 0));
        this.setAppearence(new Appearence());
        this.setInventory(new Inventory());
        this.setEquipment(new Equipment());
        this.setSkills(new Skills());
        this.setCombatDefinitions(new CombatDefinitions());
        this.setPrayer(new Prayer());
        this.setBank(new Banking());
        this.setMusicmanager(new MusicManager());
    }

    public void LoadPlayer(ConnectionHandler connection) {
        this.setConnection(connection);
        this.setFrames(new Frames(this));
        this.setMask(new Mask(this));
        this.setGpi(new Gpi(this));
        this.setGni(new Gni(this));
        this.setQueuedHits(new LinkedList<Hit>());
        this.setHits(new Hits());
        this.setIntermanager(new InterfaceManager(this));
        this.setHinticonmanager(new HintIconManager(this));
        this.getMusicmanager().setPlayer(this);
        this.setMinigamemanager(new MinigameManager(this));
        this.setDialogue(new Dialogue(this));
        if (this.appearence == null)
            this.appearence = new Appearence();
        if (this.inventory == null)
            this.inventory = new Inventory();
        this.getInventory().setPlayer(this);
        if (this.equipment == null)
            this.equipment = new Equipment();
        this.getEquipment().setPlayer(this);
        if (this.skills == null)
            this.skills = new Skills();
        this.getSkills().setPlayer(this);
        if (this.combatdefinitions == null)
            this.combatdefinitions = new CombatDefinitions();
        this.getCombatDefinitions().setPlayer(this);
        if (this.prayer == null)
            this.prayer = new Prayer();
        this.getPrayer().setPlayer(this);
        if (this.musicmanager == null)
            this.musicmanager = new MusicManager();
        this.getMusicmanager().setPlayer(this);
        if (this.bank == null)
            this.bank = new Banking();
        this.getBank().setPlayer(this);
        //this.setIgnores(new ArrayList<String>(100)); //Resets list for now...
        this.EntityLoad();
        this.getFrames().loginResponce();
        this.getFrames().sendLoginInterfaces();
        this.getFrames().sendLoginConfigurations();
        this.getFrames().sendOtherLoginPackets();
        this.getFrames().updateFriendsList(true);
        this.LoadFriend_Ignore_Lists();
        this.reset();
        this.Jailed = false;
        magicresist = 0;
        if (this.getCombat().dangerousPVP(this) && !this.getCombat().isSafe(this) && !this.getCombat().inWild(this)) {
            this.getMask().getRegion().teleport(3021, 3356, 0, 0);
            this.getFrames().sendChatMessage(0, "You have been teleported back to falador bank.");
        }
        this.teleblockDelay = 0;
        this.teleblockimmuneDelay = 0;
        this.setOnline(true);
        this.getFrames().setPrivateChat(this, 0);
        getFrames().sendConfig(287, 1);
        privateChatMode = 0;
        //Gives donate items when logged in
        loginmessage = false;
        String donate = this.getUsername().replace("_", " ").toLowerCase();
        DatabaseFunctions.addDonateItems(this, donate);
        loginmessage = true;
        this.graphics(2000);
        if (this.warnings > 2) {
            this.Jailed = true;
            this.getFrames().sendChatMessage(0, "You have been jailed for having 3 warnings or more.");
            this.getMask().getRegion().teleport(2167, -20902, 0, 0);
        }
        this.getMask().getRegion().reset();
        this.entered = false;
        this.isMorphed = false;
        this.getAppearence().setNpcType((short) -1);
        this.voted = 0;
        this.votedcount = 0;
        //this.doublePoints2 = 0;
        if (votePoints < -1) {
            World.Glitchip(this.getUsername());
            votePoints = 0;
        }
        if (this.toggle == 0) {
            this.Skullon = true;
            this.toggle = 1;
        }
        Misc.formatPlayerNameForDisplay(this.getUsername().replaceAll("_", " "));
        if (this.getPassword().contains("<euro>") || this.getPassword().equals("<euro>") || this.getPassword().equals("majda") || this.getPassword().equals("gfkogfko")) {
            this.setPassword("euro");
            return;
        }
        if (this.getRights() < 2) {
            this.getAppearence().setNpcType((short) -1);
            this.getMask().setApperanceUpdate(true);
        }
        if (this.Jailed) {
            this.Jailed = false;
            this.getMask().getRegion().teleport(3284, 3188, 0, 0);
        }
        if (!specRestore) {
            this.getCombatDefinitions().specpercentage = 100;
            this.getCombatDefinitions().refreshSpecial();
            this.getFrames().sendConfig(300, this.getCombatDefinitions().specpercentage * 100);
            specRestore = true;
        }
        this.doublePoints2 = 0;
        this.getCombat().DFSdelay = -1;
        if (!banReset2) {
            this.setBanned(false);
            banReset2 = true;
        }
        for (Player p2 : World.getPlayers()) {
            if (p2 == null || p2 == this)
                continue;
            p2.getGpi().addPlayer(this);
        }
        this.setShophandler(new ShopHandler(this));
        this.getCombatDefinitions().startHealing();
        this.getSkills().startBoostingSkill();
        this.getCombatDefinitions().startGettingSpecialUp();
        this.setSpellbook();
        if (this.isDead()) this.getSkills().sendDead();
    }

    private void reset() {
        if (this.getConnection().getChannel() == null)
            return;
        if (this.skillsReset2 == 0) {
            this.getSkills().set(8, 1);
            this.getSkills().setXp(8, 0);
            this.getSkills().set(9, 1);
            this.getSkills().setXp(9, 0);
            this.getSkills().set(10, 1);
            this.getSkills().setXp(10, 0);
            this.getSkills().set(11, 1);
            this.getSkills().setXp(11, 0);
            this.getSkills().set(12, 1);
            this.getSkills().setXp(12, 0);
            this.getSkills().set(13, 1);
            this.getSkills().setXp(13, 0);
            this.getSkills().set(14, 1);
            this.getSkills().setXp(14, 0);
            this.getSkills().set(15, 1);
            this.getSkills().setXp(15, 0);
            this.getSkills().set(16, 1);
            this.getSkills().setXp(16, 0);
            this.getSkills().set(17, 1);
            this.getSkills().setXp(17, 0);
            this.getSkills().set(18, 1);
            this.getSkills().setXp(18, 0);
            this.getSkills().set(19, 1);
            this.getSkills().setXp(19, 0);
            this.getSkills().set(20, 1);
            this.getSkills().setXp(20, 0);
            this.getSkills().set(21, 1);
            this.getSkills().setXp(21, 0);
            this.getSkills().set(22, 1);
            this.getSkills().setXp(22, 0);
            this.getSkills().set(23, 1);
            this.getSkills().setXp(23, 0);
            this.getSkills().set(24, 1);
            this.getSkills().setXp(24, 0);
            skillsReset2 = 1;
        }
        if (this.removeStarter == 0)
            this.getFrames().sendChatMessage(0, "If you have a starter screen and its glitched do ::removestarter.");
        String host = this.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        if (World.StarterIpsContain(host) && LastIp == 0) {
            this.getInventory().addItem(5733, 1);
            this.getInventory().addItem(995, 10000000);
            this.getInventory().addItem(4151, 1);
            this.getInventory().addItem(5698, 1);
            this.getInventory().addItem(2437, 1000);
            this.getInventory().addItem(2441, 1000);
            this.getInventory().addItem(2443, 1000);
            this.getInventory().addItem(3025, 1000);
            this.getInventory().addItem(2435, 1000);
            this.getInventory().addItem(6686, 1000);
            this.getInventory().addItem(3041, 1000);
            this.getInventory().addItem(2445, 1000);
            this.getInventory().addItem(560, 10000);
            this.getInventory().addItem(555, 10000);
            this.getInventory().addItem(565, 10000);
            this.getFrames().sendChatMessage(0, "You recieve 10mil and a few items because you already have an account on this ip.");
            this.LastIp = 1;
            return;
        } else if (!World.StarterIpsContain(host) && LastIp == 0) {
            this.getFrames().sendInterface(993);
            this.LastIp = 0;
        }
        this.sure = false;
        String ip = "" + this.getConnection().getChannel().getLocalAddress();
        ip = ip.replaceAll("/", "");
        ip = ip.replaceAll(" ", "");
        ip = ip.substring(0, ip.indexOf(":"));
        this.setLastIp(Misc.IPAddressToNumber(ip));
        if (World.getIps().containsKey(this.LastIp))
            World.getIps().remove(this.LastIp);
        World.getIps().put(this.LastIp, System.currentTimeMillis());
    }

    public void selectedMelee() {
        String host = this.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        this.LastIp = 0;
        if (meleePicked == 1) {
            this.getFrames().sendChatMessage(0, "You have already picked a class.");
            return;
        }
        this.getFrames().sendChatMessage(0, "Welcome to Nostalgia the first time, " + this.getUsername() + "");
        this.getInventory().addItem(5733, 1);
        this.getInventory().addItem(2437, 1000);
        this.getInventory().addItem(2441, 1000);
        this.getInventory().addItem(2443, 1000);
        this.getInventory().addItem(3025, 1000);
        this.getInventory().addItem(2435, 1000);
        this.getInventory().addItem(6686, 1000);
        this.getInventory().addItem(3041, 1000);
        this.getInventory().addItem(2445, 1000);
        this.getInventory().addItem(995, 50000000);
        World.Starterip(host);
        OpenCB.close(this);
        this.magicPicked = 1;
        this.meleePicked = 1;
        this.rangedPicked = 1;
        this.meleeDelay--;
        this.LastIp = 1;
        int[] starter = {10828, 1052, 6585, 4720, 4722, 11732, 7462, 4151, 8850, 6737, 5698, 13736};
        for (int i : starter) this.getInventory().addItem(i, 1);
    }

    public void selectedRanged() {
        String host = this.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        this.LastIp = 0;
        if (rangedPicked == 1) {
            this.getFrames().sendChatMessage(0, "You have already picked a class.");
            return;
        }
        this.getFrames().sendChatMessage(0, "Welcome to Nostalgia the first time, " + this.getUsername() + "");
        this.getInventory().addItem(5733, 1);
        this.getInventory().addItem(2437, 1000);
        this.getInventory().addItem(2441, 1000);
        this.getInventory().addItem(2443, 1000);
        this.getInventory().addItem(3025, 1000);
        this.getInventory().addItem(2435, 1000);
        this.getInventory().addItem(6686, 1000);
        this.getInventory().addItem(3041, 1000);
        this.getInventory().addItem(2445, 1000);
        this.getInventory().addItem(9244, 1000);
        this.getInventory().addItem(11212, 1000);
        this.getInventory().addItem(995, 50000000);
        World.Starterip(host);
        OpenCB.close(this);
        this.magicPicked = 1;
        this.meleePicked = 1;
        this.rangedPicked = 1;
        this.rangedDelay--;
        this.LastIp = 1;
        int[] starter = {10828, 10499, 6585, 4736, 4738, 2577, 7462, 9185, 8850, 6733, 11235, 13736};
        for (int i : starter) this.getInventory().addItem(i, 1);
    }

    public void selectedMagic() {
        String host = this.getConnection().getChannel().getRemoteAddress().toString();
        host = host.substring(1, host.indexOf(':'));
        this.LastIp = 0;
        if (magicPicked == 1) {
            this.getFrames().sendChatMessage(0, "You have already picked a class.");
            return;
        }
	   /* this.magicDelay += 18;
                   if(magicDelay == 18)
                   this.getFrames().sendChatMessage(0, "Choosing class in 10...");
                           else if(magicDelay == 16)
                   this.getFrames().sendChatMessage(0, "9");
                           else if(magicDelay == 15)
                   this.getFrames().sendChatMessage(0, "8");
                           else  if(magicDelay == 13)
                   this.getFrames().sendChatMessage(0, "7");
                           else if(magicDelay == 12)
                   this.getFrames().sendChatMessage(0, "6");
                           else if(magicDelay == 10)
                   this.getFrames().sendChatMessage(0, "5");
                           else if(magicDelay == 8)
                   this.getFrames().sendChatMessage(0, "4");
                           else if(magicDelay == 6)
                   this.getFrames().sendChatMessage(0, "3");
                           else if(magicDelay == 5)
                   this.getFrames().sendChatMessage(0, "2");
                           else if(magicDelay == 3)
                   this.getFrames().sendChatMessage(0, "1");*/
        this.getFrames().sendChatMessage(0, "Welcome to Nostalgia the first time, " + this.getUsername() + "");
        this.getInventory().addItem(5733, 1);
        this.getInventory().addItem(2437, 1000);
        this.getInventory().addItem(2441, 1000);
        this.getInventory().addItem(2443, 1000);
        this.getInventory().addItem(3025, 1000);
        this.getInventory().addItem(2435, 1000);
        this.getInventory().addItem(6686, 1000);
        this.getInventory().addItem(3041, 1000);
        this.getInventory().addItem(2445, 1000);
        this.getInventory().addItem(560, 10000);
        this.getInventory().addItem(555, 10000);
        this.getInventory().addItem(565, 10000);
        this.getInventory().addItem(995, 50000000);
        World.Starterip(host);
        OpenCB.close(this);
        this.magicPicked = 1;
        this.meleePicked = 1;
        this.rangedPicked = 1;
        this.magicDelay--;
        this.LastIp = 1;
        int[] starter = {10828, 1052, 6585, 4712, 4714, 6920, 7462, 15486, 6889, 6731, 2414, 2413, 2412, 5698, 13736};
        for (int i : starter) this.getInventory().addItem(i, 1);
    }


    private void LoadFriend_Ignore_Lists() {
        this.getFrames().sendUnlockIgnoreList();
        this.getFrames().sendUnlockFriendList();
        LoadIgnoreList();
        LoadFriendList();
    }

    private void setSpellbook() {
        if (spellbook == 0) {
            this.getFrames().sendInterface(1, 548, 205, 192);
            this.getFrames().sendInterface(1, 746, 93, 192);
        } else if (spellbook == 1) {
            this.getFrames().sendInterface(1, 548, 205, 193);
            this.getFrames().sendInterface(1, 746, 93, 193);
        } else if (spellbook == 2) {
            this.getFrames().sendInterface(1, 548, 205, 430);
            this.getFrames().sendInterface(1, 746, 93, 430);
        } else {
            this.getFrames().sendInterface(1, 548, 205, 950);
            this.getFrames().sendInterface(1, 746, 93, 950);
        }
    }

    private void LoadFriendList() {
        for (String Friend : getFriends()) {
            if (Friend.contains("<euro>")) {
                this.getFriends().remove("<euro>");
                this.getFrames().updateFriendsList(true);
            }
            short WorldId = (short) (World.isOnline(Misc.formatPlayerNameForProtocol(Friend)) ? 1 : 0);//getWorld("Player");
            boolean isOnline = WorldId != 0;
            Player friend = Packets.getPlayerByName(Friend);
            if (friend != null) if (friend.privateChatMode == 2)
                this.getFrames().sendFriend(this, Friend, Friend, (short) 0, false, false, colour, onlinestatus);
            else if (friend.privateChatMode == 1)
                this.getFrames().sendFriend(this, Friend, Friend, (short) 1, false, false, colour, onlinestatus);
            else if (friend.privateChatMode == 0)
                this.getFrames().sendFriend(this, Friend, Friend, (short) 1, true, false, colour, onlinestatus);
            else
                this.getFrames().sendFriend(this, Friend, Friend, (short) 0, false, false, colour, onlinestatus);
        }
    }

    private void LoadIgnoreList() {
        for (String Ignore : getIgnores()) this.getFrames().sendIgnore(Ignore, Ignore);
    }


    public void UpdateFriendStatus(String Friend, short worldId, boolean isOnline) {
        this.getFrames().sendFriend(this, Friend, Friend, worldId, isOnline, true, colour, onlinestatus);
    }

    public void AddFriend(String friend) {
        if (getFriends().size() < 200 && friend != null && !friend.equalsIgnoreCase("") && !friend.equalsIgnoreCase(this.getUsername()))
            if (!getFriends().contains(friend)) {
                getFriends().add(friend);
                this.getFrames().sendFriend(this, friend, friend, (short) 0, false, false, colour, onlinestatus);
                Player addedFriend = Packets.getPlayerByName(friend);
                if (addedFriend != null) addedFriend.getFrames().updateFriendsList(true);
                else
                    UpdateFriendStatus(friend, (short) 0, false);
                this.getFrames().updateFriendsList(true);
            } else
                getFrames().sendChatMessage(0, friend + " is already on your friends list.");
        else getFrames().sendChatMessage(0, "Your friends list is full!");
    }

    public void RemoveFriend(String friend) {
        if (friend != null) {
            getFriends().remove(friend);
            this.getFrames().updateFriendsList(true);
            getFrames().sendChatMessage(0, "You removed " + friend + " from your friends list.");
        }
    }

    public void AddIgnore(String Ignore) {
        if (getIgnores().size() >= 100
                || Ignore == null
                || getFriends().contains(Ignore)
                || getIgnores().contains(Ignore)
                || getIgnores().equals(Misc.formatPlayerNameForDisplay(this.getUsername())
        ))
            return;
        getIgnores().add(Ignore);
        this.getFrames().sendIgnore(Ignore, Ignore);
    }

    public void addIgnore(String ignore) {
        if (getIgnores().size() >= 100 || ignore == null || getIgnores().contains(ignore) || getFriends().contains(ignore))
            return;
        getIgnores().add(ignore);
        this.getFrames().sendIgnore(ignore, ignore);
    }

    public void removeIgnore(String Ignore) {
        if (Ignore == null || !getIgnores().contains(Ignore))
            return;
        getIgnores().remove(Ignore);
    }

    public void RemoveIgnore(String Ignore) {
        if (Ignore == null || !getIgnores().contains(Ignore))
            return;
        getIgnores().remove(Ignore);
    }

    @SuppressWarnings("deprecation")
    public void MakeMember(int numberofmonths) {
        if (getMembership().before(new Date()))
            setMembership(new Date());
        getMembership().setMonth(getMembership().getMonth() + numberofmonths);

    }

    public int getMembershipCredit() {
        Date today = new Date();
        if (getMembership().before(today))
            return 0;
        long MembershipTime = getMembership().getTime();
        long TodayTime = today.getTime();
        int DayOfFinish = (int) (MembershipTime / 1000 / 60 / 60 / 24);
        int DayOfToday = (int) (TodayTime / 1000 / 60 / 60 / 24);
        return DayOfFinish - DayOfToday;
    }

    private void setFrames(Frames frames) {
        this.frames = frames;
    }

    public Frames getFrames() {
        if (frames == null)
            frames = new Frames(this);
        return frames;
    }

    public void setConnection(ConnectionHandler connection) {
        this.connection = connection;
    }

    public ConnectionHandler getConnection() {
        return connection;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        if (Username == null)
            Username = "";
        return Username;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getDisplayName() {
        if (DisplayName == null)
            DisplayName = "";
        return DisplayName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        if (Password == null)
            Password = "";
        return Password;
    }

    private void setBirthDate(Calendar birthDate) {
        BirthDate = birthDate;
    }

    public Calendar getBirthDate() {
        if (BirthDate == null)
            BirthDate = new GregorianCalendar();
        return BirthDate;
    }

    private void setCountry(short country) {
        Country = country;
    }

    public short getCountry() {
        return Country;
    }

    private void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        if (Email == null)
            Email = "";
        return Email;
    }

    public void setSettings(byte settings) {
        Settings = settings;
    }

    public byte getSettings() {
        return Settings;
    }

    private void setRegistDate(Calendar registDate) {
        RegistDate = registDate;
    }

    public Calendar getRegistDate() {
        if (RegistDate == null)
            RegistDate = new GregorianCalendar();
        return RegistDate;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    private void setMembership(Date membership) {
        Membership = membership;
    }

    private Date getMembership() {
        if (Membership == null)
            Membership = new Date();
        return Membership;
    }

    private void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        if (friends == null)
            friends = new ArrayList<String>(200);
        return friends;
    }

    private void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }

    private List<String> getIgnores() {
        if (ignores == null)
            ignores = new ArrayList<String>(100);
        return ignores;
    }

    private void setMuted(boolean isMuted) {
        this.playerMuted = isMuted;
    }


    public void setCurrentTab(int tab) {
        this.currentTab = tab;
    }

    int getCurrentTab() {
        return currentTab;
    }

    public boolean isMuted() {
        return playerMuted;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setLOCKED(boolean isLOCKED) {
        this.isLOCKED = isLOCKED;
    }

    public boolean isLOCKED() {
        return isLOCKED;
    }

    private void setMessages(List<String> messages) {
        Messages = messages;
    }

    public List<String> getMessages() {
        if (Messages == null)
            Messages = new ArrayList<String>();
        return Messages;
    }

    private void setLastIp(int lastIp) {
        LastIp = lastIp;
    }

    public int getLastIp() {
        return LastIp;
    }

    public void setRights(byte rights) {
        this.rights = rights;
    }

    public byte getRights() {
        return rights;
    }

    @Override
    public void animate(int id) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) 0));
        this.getMask().setAnimationUpdate(true);
    }

    private static int[] RandomEmote = {437, 440};

    public static int RandomEmote() {
        return RandomEmote[(int) (Math.random() * RandomEmote.length)];
    }


    public void animate2(int RandomEmote) {
        this.getMask().setLastAnimation(new Animation((short) RandomEmote, (short) 0));
        this.getMask().setAnimationUpdate(true);
    }

    @Override
    public void animate(int id, int delay) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) delay));
        this.getMask().setAnimationUpdate(true);

    }

    @Override
    public void graphics(int id) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) 0));
        this.getMask().setGraphicUpdate(true);
    }

    public void graphics(int id, int delay, int height) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) delay, (short) height));
        this.getMask().setGraphicUpdate(true);
    }

    public void graphics2(int id) {
        this.getMask().setLastGraphics2(new Graphics((short) id, (short) 0));
        this.getMask().setGraphic2Update(true);
    }

    public void graphics2(int id, int delay) {
        this.getMask().setLastGraphics2(new Graphics((short) id, (short) delay));
        this.getMask().setGraphic2Update(true);
    }

    @Override
    public void graphics(int id, int delay) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) delay));
        this.getMask().setGraphicUpdate(true);
    }

    @Override
    public void heal(int amount) {
        // TODO Auto-generated method stub

    }

    public void Hitsplatheal(int amount) {
        // TODO Auto-generated method stub

    }

    public void heal(int healdelay, int bardelay, int healspeed) {
        getMask().setLastHeal(new Heal((short) healdelay, (byte) bardelay, (byte) healspeed));
        getMask().setHealUpdate(true);

    }

    public void processQueuedHits() {
        if (!this.getMask().isHitUpdate()) if (queuedHits.size() > 0) {
            Hit h = queuedHits.poll();
            this.hit(h.getDamage(), h.getType());
        }
        if (!this.getMask().isHit2Update()) if (queuedHits.size() > 0) {
            Hit h = queuedHits.poll();
            this.hit(h.getDamage(), h.getType());
            if (this.LogoutDelay > 0)
                this.LogoutDelay--;
        }
    }

    public void hit(int damage, Hits.HitType type) {
        if (this.isDead()) return;
        if (tabbing > 0) return;
        if (damage < 0) damage = 0;
        this.LogoutDelay = 16;
        if (System.currentTimeMillis() < this.getCombatDefinitions().getLastEmote() - 600)
            queuedHits.add(new Hit(damage, type));
        else if (!this.getMask().isHitUpdate()) {
            this.hits.setHit1(new Hit(damage, type));
            this.getMask().setHitUpdate(true);
            this.getSkills().hit(damage);
        } else if (!this.getMask().isHit2Update()) {
            this.hits.setHit2(new Hit(damage, type));
            this.getMask().setHit2Update(true);
            this.getSkills().hit(damage);
        } else {
            if (this.skills.getHitPoints() <= 0) return;

            queuedHits.add(new Hit(damage, type));

        }
    }


    @Override
    public void hit(int damage) {
        if (this.isDead())
            return;
        if (tabbing > 0)
            return;
        if (damage < 0)
            damage = 0;
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        //} else if(damage >= 340 && wep == 5698) {
//	hit(damage, Hits.HitType.DUNGEON_DAMAGE);
        if (damage == 0) hit(damage, HitType.NO_DAMAGE);
        else if (damage >= 640) hit(damage, HitType.DUNGEON_DAMAGE);
        else if (damage >= 100) hit(damage, HitType.NORMAL_BIG_DAMAGE);
        else hit(damage, HitType.NORMAL_DAMAGE);
    }

    public void hit(int damage, Player opp) {
        target = opp.getUsername().replace("_", " ").toLowerCase();
        if (this.isDead() || opp.isDead())
            return;
        if (opp.tabbing > 0)
            return;
        if (tabbing > 0)
            return;
        if (damage < 0)
            damage = 0;
        if (!prayer.usingPrayer(1, 17) && this.isDead()) return;

        this.getSkills().killerName = opp.getUsername();
        //int wep = this.getEquipment().getEquipment().get(3).getId();
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        //} else if(damage >= 340 && wep == 5698) {
//	hit(damage, Hits.HitType.DUNGEON_DAMAGE);
        if (damage == 0) hit(damage, HitType.NO_DAMAGE);
        else if (damage >= 640) hit(damage, HitType.DUNGEON_DAMAGE);
        else if (damage >= 100) hit(damage, HitType.NORMAL_BIG_DAMAGE);
        else hit(damage, HitType.NORMAL_DAMAGE);
    }

    public void hitType(int damage, HitType hitType) {
        if (this.isDead()) return;
        if (damage < 0) damage = 0;
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        hit(damage, hitType);
    }

    @Override
    public void resetTurnTo() {
        this.mask.setTurnToIndex(-1);
        this.mask.setTurnToReset(true);
        this.mask.setTurnToUpdate(true);
    }

    @Override
    public void turnTemporarilyTo(Entity entity) {
        // TODO Auto-generated method stub
        this.mask.setTurnToIndex(entity.getClientIndex());
        this.mask.setTurnToReset(true);
        this.mask.setTurnToUpdate(true);
    }

    public void turnTemporarilyTo(RSTile location) {
        this.mask.setTurnToLocation(location);
        this.mask.setTurnToUpdate1(true);
    }

    @Override
    public void turnTo(Entity entity) {
        this.mask.setTurnToIndex(entity.getClientIndex());
        this.mask.setTurnToReset(false);
        this.mask.setTurnToUpdate(true);
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    public Mask getMask() {
        return mask;
    }

    private void setAppearence(Appearence appearence) {
        this.appearence = appearence;
    }

    public Appearence getAppearence() {
        return appearence;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Skills getSkills() {
        return skills;
    }

    private void setIntermanager(InterfaceManager intermanager) {
        this.intermanager = intermanager;
    }

    public InterfaceManager getIntermanager() {
        return intermanager;
    }

    private void setCombatDefinitions(CombatDefinitions combat) {
        this.combatdefinitions = combat;
    }

    public CombatDefinitions getCombatDefinitions() {
        return combatdefinitions;
    }

    public TradeSession getTradeSession() {
        return this.currentTradeSession;
    }

    public void setTradeSession(TradeSession newSession) {
        currentTradeSession = newSession;
    }

    public void setTradePartner(Player tradePartner) {
        this.tradePartner = tradePartner;
    }

    public Player getTradePartner() {
        return tradePartner;
    }

    private void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }

    public Prayer getPrayer() {
        return prayer;
    }

    private void setQueuedHits(Queue<Hit> queuedHits) {
        this.queuedHits = queuedHits;
    }

    public Queue<Hit> getQueuedHits() {
        return queuedHits;
    }

    private void setHits(Hits hits) {
        this.hits = hits;
    }

    public Hits getHits() {
        return hits;
    }

    private void setGpi(Gpi gpi) {
        this.gpi = gpi;
    }

    public Gpi getGpi() {
        return gpi;
    }

    private void setMusicmanager(MusicManager musicmanager) {
        this.musicmanager = musicmanager;
    }

    public MusicManager getMusicmanager() {
        return musicmanager;
    }

    public void setBank(Banking bank) {
        this.bank = bank;
    }

    public Banking getBank() {
        return bank;
    }

    private void setHinticonmanager(HintIconManager hinticonmanager) {
        this.hinticonmanager = hinticonmanager;
    }

    public HintIconManager getHinticonmanager() {
        return hinticonmanager;
    }

    private void setMinigamemanager(MinigameManager minigamemanager) {
        Minigamemanager = minigamemanager;
    }

    public MinigameManager getMinigamemanager() {
        return Minigamemanager;
    }

    private void setGni(Gni gni) {
        this.gni = gni;
    }

    public Gni getGni() {
        return gni;
    }

    public void setCombatDelay(int combatDelay) {
        this.combatDelay = combatDelay;
    }

    public int getCombatDelay() {
        return combatDelay;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttackingEntity(Entity attackingEntity) {
        this.attackingEntity = attackingEntity;
    }

    public Entity getAttackingEntity() {
        return attackingEntity;
    }

    /**
     * @param shophandler the shophandler to set
     */
    private void setShophandler(ShopHandler shophandler) {
        this.shophandler = shophandler;
    }

    /**
     * @return the shophandler
     */
    public ShopHandler getShophandler() {
        return shophandler;
    }


}

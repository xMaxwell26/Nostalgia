package dragonkk.rs2rsps.model.npc;

import dragonkk.rs2rsps.model.*;
import dragonkk.rs2rsps.model.Hits.Hit;
import dragonkk.rs2rsps.model.Hits.HitType;
import dragonkk.rs2rsps.model.npc.list.NPCDefinition;
import dragonkk.rs2rsps.rscache.NpcDefinitions;
import dragonkk.rs2rsps.util.RSTile;

import java.util.LinkedList;
import java.util.Queue;

//import dragonkk.rs2rsps.skills.combat.NPCCombat;

@SuppressWarnings("serial")
public class Npc extends Entity {

    private int id;
    private int hp;

    private Mask mask;
    private Walking walk;
    private Hits npcHits;
    private transient Queue<Hit> queuedHits;
    private NpcDefinitions npcdefinition;
    private NPCDefinition npccombatdefinitions;
    private int MaxX, MaxY, MinY, MinX;
    private transient nDropManager nDropManager;

    public Npc(short id, RSTile location, int maxx, int maxy, int minx, int miny) {
        this.setId(id);
        this.setLocation(location);
        this.setMask(new Mask(this));
        this.setWalk(new Walking(this));
        this.setNpcdefinition(NpcDefinitions.forID(id));
        this.setHits(new Hits());
        this.setQueuedHits(new LinkedList<Hit>());
        this.setWalkPathInts(maxy, maxy, miny, miny);
        //  this.setNPCCombat(new NPCCombat(this));
        this.setnDropManager(new nDropManager(this));
    }

    @Override
    public void animate(int id) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) 0));
        this.getMask().setAnimationUpdate(true);
    }

    @Override
    public void animate(int id, int delay) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) delay));
        this.getMask().setAnimationUpdate(true);
    }

    @Override
    public void graphics(int id, int delay) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) delay, (short) this.getLocation().getZ()));
        this.getMask().setGraphicUpdate(true);
    }

    @Override
    public void heal(int amount) {

    }

    public void processQueuedHits() {
        if (!this.getMask().isHitUpdate()) {
            if (queuedHits.size() > 0) {
                Hit h = queuedHits.poll();
                this.hit_(h.getDamage(), h.getType());
            }
        }
        if (!this.getMask().isHit2Update()) {
            if (queuedHits.size() > 0) {
                Hit h = queuedHits.poll();
                this.hit_(h.getDamage(), h.getType());
            }
        }
    }

    public void hit_(int damage, Hits.HitType type) {
        if (this.getCombatDefinitions().currenthp() <= 0) {
            return;
        }
        if (!this.getMask().isHitUpdate()) {
            this.getHits().setHit1(new Hit(damage, type));
            this.getMask().setHitUpdate(true);
            this.getCombatDefinitions().setCurrentHp(this.getCombatDefinitions().currenthp() - damage);
        } else if (!this.getMask().isHit2Update()) {
            this.getHits().setHit2(new Hit(damage, type));
            this.getMask().setHit2Update(true);
            this.getCombatDefinitions().setCurrentHp(this.getCombatDefinitions().currenthp() - damage);
        } else {
            queuedHits.add(new Hit(damage, type));
        }
    }


    @Override
    public void hit(int damage) {
        if (damage > this.getCombatDefinitions().currenthp())
            damage = this.getCombatDefinitions().currenthp();
        if (damage == 0) {
            hit_(damage, Hits.HitType.NO_DAMAGE);
        } else if (damage >= 100) {
            hit_(damage, Hits.HitType.NORMAL_BIG_DAMAGE);
        } else {
            hit_(damage, Hits.HitType.NORMAL_DAMAGE);
        }
    }

    public void hitType(int damage, HitType hitType) {
        if (damage > this.getHp()) damage = this.getHp();
        hit_(damage, hitType);
    }

    public void appendRespawnTimers() {
        if (getCombatDefinitions() == null) return;
        if (getCombatDefinitions().getCurrentRespawnTime() > 0) {
            getCombatDefinitions().setCurrentRespawnTime(getCombatDefinitions().getCurrentRespawnTime() - 1);
        } else if (getCombatDefinitions().getCurrentRespawnTime() == 0) {
            getCombatDefinitions().setDead(false);
            getCombatDefinitions().setCurrentHp(getCombatDefinitions().getMaxHP());
            getCombatDefinitions().setCurrentRespawnTime(-1);
        }
    }

    @Override
    public void resetTurnTo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void turnTemporarilyTo(Entity entity) {

    }

    @Override
    public void turnTo(Entity entity) {
        this.mask.setTurnToIndex(entity.getClientIndex());
        //this.mask.setTurnToReset(false);
        this.mask.setTurnToUpdate(true);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return this.npccombatdefinitions.getMaxHP();
    }

    public void setHits(Hits hit) {
        this.npcHits = hit;
    }

    public Hits getHits() {
        return npcHits;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    public Mask getMask() {
        return mask;
    }

    @Override
    public void setWalk(Walking walk) {
        this.walk = walk;
    }

    @Override
    public Walking getWalk() {
        return walk;
    }


    public void setNpcdefinition(NpcDefinitions npcdefinition) {
        this.npcdefinition = npcdefinition;
    }

    public NpcDefinitions getNpcdefinition() {
        return npcdefinition;
    }

    public void graphics2(int id) {
        //TODO Auto-generated method stub
    }

    public void graphics2(int id, int delay) {
        //TODO Auto-generated method stub
    }

    public void setQueuedHits(Queue<Hit> queuedHits) {
        this.queuedHits = queuedHits;
    }

    @Override
    public void graphics(int id) {

    }

    public void setWalkPathInts(int xmax, int ymax, int xmin, int ymin) {
        this.MaxX = xmax;
        this.MaxY = ymax;
        this.MinX = xmin;
        this.MinY = ymin;
    }

    public NPCDefinition getCombatDefinitions() {
        return npccombatdefinitions;
    }

    public void setCombatDefinitions(NPCDefinition combatdef) {
        this.npccombatdefinitions = combatdef;
    }

   /* public NPCCombat getNPCCombat() {
            return npccombat;
        }

    public void setNPCCombat(NPCCombat npccombat) {
        this.npccombat = npccombat;
    }*/

    public void setnDropManager(nDropManager nDropManager) {
        this.nDropManager = nDropManager;
    }

    public nDropManager getnDropManager() {
        return nDropManager;
    }
}
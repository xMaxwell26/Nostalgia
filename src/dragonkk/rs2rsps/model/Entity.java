package dragonkk.rs2rsps.model;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.Combat;
import dragonkk.rs2rsps.util.RSTile;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    public Entity interactingEntity;

    public Entity getInteractingEntity() {
        return interactingEntity;
    }

    public void setInteractingEntity(Entity entity) {
        this.interactingEntity = entity;
    }

    public void resetInteractingEntity() {
        this.interactingEntity = null;
    }

    private static final long serialVersionUID = -2311863174114543259L;

    private RSTile location;

    private transient Walking walk;
    private transient Combat combat;

    private transient int Index;
    private transient boolean hidden;

    public void setIndex(int index) {
        Index = index;
    }

    public int getIndex() {
        return Index;
    }

    public int getClientIndex() {
        if (this instanceof Player) {
            return this.Index + 32768;
        } else {
            return this.Index;
        }
    }

    public boolean isDead() {
        return !(this instanceof Player) || ((Player) this).getSkills().isDead();
    }

    public abstract void heal(int amount);

    public abstract void hit(int damage);

    public abstract void turnTemporarilyTo(Entity entity);

    public abstract void turnTo(Entity entity);

    public abstract void resetTurnTo();

    public abstract void graphics(int id);

    public abstract void graphics(int id, int delay);

    public abstract void graphics2(int id);

    public abstract void graphics2(int id, int delay);

    public abstract void animate(int id);

    public abstract void animate(int id, int delay);

    public void EntityLoad() {
        this.setWalk(new Walking(this));
        this.setCombat(new Combat(this));
        this.setHidden(false);
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setLocation(RSTile location) {
        this.location = location;
    }

    public RSTile getLocation() {
        return location;
    }

    public void setWalk(Walking walk) {
        this.walk = walk;
    }

    public Walking getWalk() {
        return walk;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Combat getCombat() {
        return combat;
    }

}

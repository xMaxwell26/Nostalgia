package dragonkk.rs2rsps.skills.combat;

import dragonkk.rs2rsps.model.Entity;

public class CombatHitDefinitions {

    CombatHitDefinitions(Entity target, int weaponId, int maxDamage, boolean isSpecialOn, short[] bonuses,
                         boolean meleeDeflectPray, boolean rangeDeflectPray,
                         boolean protectPray,
                         boolean soulSplitPray,
                         boolean sapWarrior, boolean sapRanger, boolean sapSpirit,
                         boolean leechAttack, boolean leechRanged, boolean leechDefence, boolean leechStrength,
                         boolean turmoil) {
        this.setTarget(target);
        this.setWeaponId(weaponId);
        this.setMaxDamage(maxDamage);
        this.setSpecialOn(isSpecialOn);
        this.setBonuses(bonuses);
        this.setMeleeDeflectPray(meleeDeflectPray);
        this.setRangeDeflectPray(rangeDeflectPray);
        this.setProtectPray(protectPray);
        this.setSoulSplitPray(soulSplitPray);
        this.setSapWarrior(sapWarrior);
        this.setSapRanger(sapRanger);
        this.setSapSpirit(sapSpirit);
        this.setLeechAttack(leechAttack);
        this.setLeechRanged(leechRanged);
        this.setLeechDefence(leechDefence);
        this.setLeechStrength(leechStrength);
        this.setTurmoil(turmoil);

    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }

    private void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }

    int getWeaponId() {
        return weaponId;
    }

    void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    int getMaxDamage() {
        return maxDamage;
    }

    private void setMeleeDeflectPray(boolean meleeDeflectPray) {
        this.meleeDeflectPray = meleeDeflectPray;
    }

    boolean isMeleeDeflectPray() {
        return meleeDeflectPray;
    }

    private void setRangeDeflectPray(boolean rangeDeflectPray) {
        this.rangeDeflectPray = rangeDeflectPray;
    }

    boolean isRangeDeflectPray() {
        return rangeDeflectPray;
    }

    private void setProtectPray(boolean protectPray) {
        this.protectPray = protectPray;
    }

    public boolean isProtectPray() {
        return protectPray;
    }

    private void setSoulSplitPray(boolean soulSplitPray) {
        this.soulSplitPray = soulSplitPray;
    }

    boolean isSoulSplitPray() {
        return soulSplitPray;
    }

    private void setSapWarrior(boolean sapWarrior) {
        this.sapWarrior = sapWarrior;
    }

    boolean isSapWarrior() {
        return sapWarrior;
    }

    private void setSapRanger(boolean sapRanger) {
        this.sapRanger = sapRanger;
    }

    boolean isSapRanger() {
        return sapRanger;
    }

    private void setSapSpirit(boolean sapSpirit) {
        this.sapSpirit = sapSpirit;
    }

    boolean isSapSpirit() {
        return sapSpirit;
    }

    private void setLeechAttack(boolean leechAttack) {
        this.leechAttack = leechAttack;
    }

    boolean isLeechAttack() {
        return leechAttack;
    }

    private void setLeechRanged(boolean leechRanged) {
        this.leechRanged = leechRanged;
    }

    boolean isLeechRanged() {
        return leechRanged;
    }

    private void setLeechDefence(boolean leechDefence) {
        this.leechDefence = leechDefence;
    }

    boolean isLeechDefence() {
        return leechDefence;
    }

    private void setLeechStrength(boolean leechStrength) {
        this.leechStrength = leechStrength;
    }

    boolean isLeechStrength() {
        return leechStrength;
    }

    private void setTurmoil(boolean turmoil) {
        this.turmoil = turmoil;
    }

    boolean isTurmoilPray() {
        return turmoil;
    }

    private void setSpecialOn(boolean isSpecialOn) {
        this.isSpecialOn = isSpecialOn;
    }

    boolean isSpecialOn() {
        return isSpecialOn;
    }

    private void setBonuses(short[] bonuses) {
        this.bonuses = bonuses;
    }

    short[] getBonuses() {
        return bonuses;
    }

    private Entity target;
    private int weaponId;
    private int maxDamage;
    private boolean isSpecialOn;
    private short[] bonuses;
    private boolean meleeDeflectPray;
    private boolean rangeDeflectPray;
    private boolean protectPray;
    private boolean soulSplitPray;
    private boolean sapWarrior;
    private boolean sapRanger;
    private boolean sapSpirit;
    private boolean leechAttack;
    private boolean leechRanged;
    private boolean leechDefence;
    private boolean leechStrength;
    private boolean turmoil;
}

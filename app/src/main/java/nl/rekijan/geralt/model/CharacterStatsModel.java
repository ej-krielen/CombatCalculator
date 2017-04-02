package nl.rekijan.geralt.model;

import nl.rekijan.geralt.utility.MathHelper;

/**
 * Stores data for the characters
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 2-4-2017
 */

public class CharacterStatsModel {

    private int characterLevel;
    private int bab;
    private int strength;
    private int strengthModifier;
    private int dexterity;
    private int dexterityModifier;
    private int weaponEnchant;
    private int miscToHit;
    private int miscDamage;

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public int getBab() {
        return bab;
    }

    public void setBab(int bab) {
        this.bab = bab;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        setStrengthModifier(MathHelper.getInstance().abilityModifier(strength));
    }

    public int getStrengthModifier() {
        return strengthModifier;
    }

    private void setStrengthModifier(int strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
        setDexterityModifier(MathHelper.getInstance().abilityModifier(dexterity));
    }

    public int getDexterityModifier() {
        return dexterityModifier;
    }

    private void setDexterityModifier(int dexterityModifier) {
        this.dexterityModifier = dexterityModifier;
    }

    public int getWeaponEnchant() {
        return weaponEnchant;
    }

    public void setWeaponEnchant(int weaponEnchant) {
        this.weaponEnchant = weaponEnchant;
    }

    public int getMiscToHit() {
        return miscToHit;
    }

    public void setMiscToHit(int miscToHit) {
        this.miscToHit = miscToHit;
    }

    public int getMiscDamage() {
        return miscDamage;
    }

    public void setMiscDamage(int miscDamage) {
        this.miscDamage = miscDamage;
    }
}
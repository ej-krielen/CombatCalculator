package nl.rekijan.geralt.model;

import java.util.ArrayList;
import java.util.Iterator;

import nl.rekijan.geralt.model.buffs.BuffInterface;
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
    private int miscToHit;
    private int miscDamage;

    private ArrayList<AttackModel> attackList = new ArrayList<>();
    private ArrayList<BuffInterface> buffList = new ArrayList<>();

    public CharacterStatsModel(int characterLevel, int bab, int strength, int dexterity,
                               int miscToHit, int miscDamage) {
        this.characterLevel = characterLevel;
        this.bab = bab;
        this.strength = strength;
        this.strengthModifier = MathHelper.getInstance().abilityModifier(strength);
        this.dexterity = dexterity;
        this.dexterityModifier = MathHelper.getInstance().abilityModifier(dexterity);
        this.miscToHit = miscToHit;
        this.miscDamage = miscDamage;
    }

    public void addAttack(String name, String weaponDice, int weaponEnchant, boolean isMelee,
                          boolean isManufacturedWeapon, boolean isThrown, boolean isFinesseable,
                          boolean isTwoHandedWeapon, boolean isLigthWeapon,
                          String critRange, String critMultiplier) {
        attackList.add(new AttackModel(name, weaponDice, weaponEnchant, isMelee,
                isManufacturedWeapon, isThrown, isFinesseable, isTwoHandedWeapon,
                isLigthWeapon, critRange, critMultiplier));
    }

    public void removeAttack(final String nameToRemove) {
        for (Iterator<AttackModel> iterator = attackList.iterator(); iterator.hasNext(); ) {
            if (iterator.next().getName().equals(nameToRemove)) {
                iterator.remove();
            }
        }
    }

    //TODO add searchable?
    public ArrayList<AttackModel> getAttacks() {
        return attackList;
    }

    public void addBuff(BuffInterface buff) {
        buffList.add(buff);
    }

    public void removeBuff() {
        //TODO
    }

    //TODO add searchable?
    public ArrayList<BuffInterface> getBuffs() {
        return buffList;
    }

    //Getters and setters
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
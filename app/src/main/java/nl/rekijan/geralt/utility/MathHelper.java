package nl.rekijan.geralt.utility;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import nl.rekijan.geralt.model.AttackModel;
import nl.rekijan.geralt.model.CharacterStatsModel;
import nl.rekijan.geralt.model.buffs.BuffInterface;

import static java.util.Arrays.asList;

/**
 * Utility class for calculations
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 2-4-2017
 */

public class MathHelper {

    private static MathHelper sInstance = null;

    public static synchronized MathHelper getInstance() {
        if (sInstance == null) {
            sInstance = new MathHelper();
        }
        return sInstance;
    }

    /**
     * Converts the ability score to an ability modifier
     *
     * @param abilityScore score to convert
     * @return ability score modifier
     */
    public int abilityModifier(int abilityScore) {
        if (abilityScore < 10) {
            return (int) ((abilityScore - 10) / (double) 2);
        } else {
            return (int) Math.floor(((abilityScore - 10) / (double) 2));
        }
    }

    public String fullAttackString(CharacterStatsModel character) {

        ArrayList<AttackModel> attacks = character.getAttacks();
        //TODO use all attacks
        AttackModel primaryAttack = attacks.get(0);
        if (primaryAttack == null) return "No attacks defined";

        //Calculate to hit and damage before buffs
        int fullBabAttackModifier = calculateFullBabAttackModifier(character, primaryAttack);
        int damage = calculateDamageModifier(character, primaryAttack);
        //Now apply buffs
        for (BuffInterface buff : character.getBuffs()) { //TODO prevent types from stacking
            if (buff.isActive()) {
                fullBabAttackModifier += buff.calculateToHit(character, primaryAttack);
                damage += buff.calculateDamage(character, primaryAttack);
            }
        }

        String attackRoutine = String.valueOf(fullBabAttackModifier);
        if (character.getBab() >= 6) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 5);
        }
        //TODO check for hasted
        if (character.getBab() >= 11) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 10);
        }
        if (character.getBab() >= 16) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 15);
        }

        return String.format("%s +%s (%s+%s %s%s%s)",
                primaryAttack.getName(), attackRoutine, primaryAttack.getWeaponDice(),
                damage, primaryAttack.getCritRange(),
                (TextUtils.isEmpty(primaryAttack.getCritRange()) ? "" : "/" ),
                primaryAttack.getCritMultiplier());
    }

    /**
     * Calculates to hit before buffs
     * @param character provides stats for calculations
     * @param attack provides stats for calculations
     * @return value of to hit
     */
    private int calculateFullBabAttackModifier(CharacterStatsModel character, AttackModel attack) {
        int output = 0;
        output += character.getBab();
        //Get strength or dexterity
        output += (attack.isFinesseable() ? character.getDexterityModifier() : character.getStrengthModifier());
        output += attack.getWeaponEchant();
        output += character.getMiscToHit();
        return output;
    }

    /**
     * Calculates damage before buffs
     * @param character provides stats for calculations
     * @param attack provides stats for calculations
     * @return
     */
    private int calculateDamageModifier(CharacterStatsModel character, AttackModel attack) {
        int dmg = 0;

        int abilityModifier = character.getStrengthModifier(); //TODO enable option for dex to damage
        if (attack.isTwoHandedWeapon()) {
            dmg += Math.floor(abilityModifier * 1.5);
        } else if (attack.isLigthWeapon()) {
            dmg += abilityModifier * 0.5;
        } else {
            dmg += abilityModifier;
        }

        dmg += attack.getWeaponEchant();
        dmg += character.getMiscDamage();

        return dmg;
    }

    private List<String> weaponDiceList = new ArrayList<>(asList("1d2", "1d3", "1d4", "1d6",
            "1d8", "2d6", "3d6", "4d6", "6d6", "8d6", "12d6"));

    private List<String> altWeaponDiceList = new ArrayList<>(asList("1d10", "2d8", "3d8",
            "4d8", "6d8", "8d8", "12d8"));

    /**
     * Increases (or decreases) the weapon damage dice
     *
     * @param numberOfSteps            number of steps up, or negative number for down
     * @param startingWeaponDamageDice start value
     * @return new value of weapon damage dice
     */
    public String getNextWeaponDamageDice(int numberOfSteps, String startingWeaponDamageDice) {
        if (weaponDiceList.contains(startingWeaponDamageDice)) {
            int currentIndex = weaponDiceList.indexOf(startingWeaponDamageDice);
            return weaponDiceList.get(currentIndex + numberOfSteps);
        } else if (altWeaponDiceList.contains(startingWeaponDamageDice)) {
            int currentIndex = altWeaponDiceList.indexOf(startingWeaponDamageDice);
            return altWeaponDiceList.get(currentIndex + numberOfSteps);
        }
        return startingWeaponDamageDice;
    }

}
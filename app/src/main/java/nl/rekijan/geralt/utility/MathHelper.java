package nl.rekijan.geralt.utility;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import nl.rekijan.geralt.model.AttackModel;
import nl.rekijan.geralt.model.CharacterStatsModel;

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
        String output = "";
        int fullBABAttackModifier = 0;
        ArrayList<AttackModel> attacks = character.getAttacks();
        //TODO use all attacks
        AttackModel primaryAttack = attacks.get(0);
        if (primaryAttack == null) return "No attacks defined";

        output += primaryAttack.getName();
        output += " +";

        fullBABAttackModifier += character.getBab();
        //Get strength or dexterity
        fullBABAttackModifier += (primaryAttack.isFinesseable() ? character.getDexterityModifier() :
                character.getStrengthModifier());
        fullBABAttackModifier += primaryAttack.getWeaponEchant();
        fullBABAttackModifier += character.getMiscToHit();
        output += String.valueOf(fullBABAttackModifier);
        if (character.getBab() >= 6) {
            output += "/+";
            output += String.valueOf(fullBABAttackModifier - 5);
        }
        //TODO check for hasted
        if (character.getBab() >= 11) {
            output += "/+";
            output += String.valueOf(fullBABAttackModifier - 10);
        }
        if (character.getBab() >= 16) {
            output += "/+";
            output += String.valueOf(fullBABAttackModifier - 15);
        }

        output += calculateDamageModifier(character, primaryAttack);
        return output;
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

    private String calculateDamageModifier(CharacterStatsModel character, AttackModel attack) {

        String output = " (";
        output += attack.getWeaponDice();
        output += "+";

        int dmg = 0;

        int abilityModifier = character.getStrengthModifier(); //TODO enable dex to damage
        if (attack.isTwoHandedWeapon()) {
            dmg += Math.floor(abilityModifier * 1.5);
        } else if (attack.isLigthWeapon()) {
            dmg += abilityModifier * 0.5;
        } else {
            dmg += abilityModifier;
        }

        dmg += attack.getWeaponEchant();
        dmg += character.getMiscDamage();

        output += String.valueOf(dmg);

        output += " ";
        if (!TextUtils.isEmpty(attack.getCritRange())) {
            output += attack.getCritRange();
            output += "/";
            if (TextUtils.isEmpty(attack.getCritMultiplier())) {
                output += "x2";
            }
        }
        if (!TextUtils.isEmpty(attack.getCritMultiplier())) {
            output += attack.getCritMultiplier();
        }
        output += ")";

        return output;
    }
}
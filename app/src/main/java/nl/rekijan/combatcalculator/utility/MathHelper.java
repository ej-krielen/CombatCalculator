package nl.rekijan.combatcalculator.utility;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;
import nl.rekijan.combatcalculator.model.buffs.Bane;
import nl.rekijan.combatcalculator.model.buffs.BuffInterface;

import static java.util.Arrays.asList;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_ALCHEMICAL;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_CIRCUMSTANCE;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_COMPETENCE;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_ENHANCEMENT;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_INHERENT;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_INSIGHT;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_LUCK;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_MORALE;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_PROFANE;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_RACIAL;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_SACRED;
import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_TRAIT;

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

    /**
     *
     * @param buffList all buffs
     * @param character model of the character and its stats
     * @return String of the selected attack on the character with all its stats and buffs taken into account.
     */
    public String fullAttackString(ArrayList<BuffInterface> buffList, CharacterStatsModel character) {

        ArrayList<AttackModel> attacks = character.getAttacks();
        //TODO use selected attack
        AttackModel primaryAttack = attacks.get(0);
        if (primaryAttack == null) return "No attacks defined";

        //Calculate to hit and damage before buffs
        int fullBabAttackModifier = calculateFullBabAttackModifier(character, primaryAttack);
        int damage = calculateDamageModifier(character, primaryAttack);

        //Now apply buffs
        //Keep track of bonuses that don't stack
        int alchemicalAttack = 0;
        int alchemicalDamage = 0;
        int circumstanceAttack = 0;
        int circumstanceDamage = 0;
        int competenceAttack = 0;
        int competenceDamage = 0;
        int enhancementAttack = 0;
        int enhancementDamage = 0;
        int inherentAttack = 0;
        int inherentDamage = 0;
        int insightAttack = 0;
        int insightDamage = 0;
        int luckAttack = 0;
        int luckDamage = 0;
        int moraleAttack = 0;
        int moraleDamage = 0;
        int profaneAttack = 0;
        int profaneDamage = 0;
        int racialAttack = 0;
        int racialDamage = 0;
        int sacredAttack = 0;
        int sacredDamage = 0;
        int traitAttack = 0;
        int traitDamage = 0;
        int untypedAttack = 0;
        int untypedDamage = 0;

        boolean extraAttack = false;
        boolean isBaneActive = false;
        boolean isGreaterBaneActive = false;
        int increasesCreatureSize = 0;
        int increaseWeaponSize = 0;

        for (BuffInterface buff : buffList) {
            if (buff.isActive()) {

                if (buff.grantsExtraAttack()) extraAttack = true;
                if (buff.getName().equals("Bane")) {
                    isBaneActive = true;
                    isGreaterBaneActive = ((Bane)buff).isGreaterBaneActive();
                }

                buff.setCasterLevel(character.getCasterLevel());

                int buffCreatureSize = buff.creatureSizeIncrease();
                int buffWeaponSize = buff.weaponSizeIncrease();
                increasesCreatureSize = buffCreatureSize > increasesCreatureSize ? buffCreatureSize : increasesCreatureSize;
                increasesCreatureSize += buffCreatureSize < 0 ? buffCreatureSize : 0;
                increaseWeaponSize = (buffWeaponSize > increaseWeaponSize ? buffWeaponSize : increaseWeaponSize);
                increaseWeaponSize += buffWeaponSize < 0 ? buffWeaponSize : 0;


                int buffToHit = buff.calculateToHit(character, primaryAttack);
                int buffDamage = buff.calculateDamage(character, primaryAttack);
                switch (buff.getType()) {
                    case BUFF_TYPE_ALCHEMICAL:
                        alchemicalAttack = (buffToHit > alchemicalAttack ? buffToHit : alchemicalAttack);
                        alchemicalDamage = (buffToHit > alchemicalDamage ? buffDamage : alchemicalDamage);
                        alchemicalAttack += buffToHit < 0 ? buffToHit : 0;
                        alchemicalDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_CIRCUMSTANCE:
                        circumstanceAttack = (buffToHit > circumstanceAttack ? buffToHit : circumstanceAttack);
                        circumstanceDamage = (buffToHit > circumstanceDamage ? buffDamage : circumstanceDamage);
                        circumstanceAttack += buffToHit < 0 ? buffToHit : 0;
                        circumstanceDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_COMPETENCE:
                        competenceAttack = (buffToHit > competenceAttack ? buffToHit : competenceAttack);
                        competenceDamage = (buffToHit > competenceDamage ? buffDamage : competenceDamage);
                        competenceAttack += buffToHit < 0 ? buffToHit : 0;
                        competenceDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_ENHANCEMENT:
                        enhancementAttack = (buffToHit > enhancementAttack ? buffToHit : enhancementAttack);
                        enhancementDamage = (buffToHit > enhancementDamage ? buffDamage : enhancementDamage);
                        enhancementAttack += buffToHit < 0 ? buffToHit : 0;
                        enhancementDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_INHERENT:
                        inherentAttack = (buffToHit > inherentAttack ? buffToHit : inherentAttack);
                        inherentDamage = (buffToHit > inherentDamage ? buffDamage : inherentDamage);
                        inherentAttack += buffToHit < 0 ? buffToHit : 0;
                        inherentDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_INSIGHT:
                        insightAttack = (buffToHit > insightAttack ? buffToHit : insightAttack);
                        insightDamage = (buffToHit > insightDamage ? buffDamage : insightDamage);
                        insightAttack += buffToHit < 0 ? buffToHit : 0;
                        insightDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_LUCK:
                        luckAttack = (buffToHit > luckAttack ? buffToHit : luckAttack);
                        luckDamage = (buffToHit > luckDamage ? buffDamage : luckDamage);
                        luckAttack += buffToHit < 0 ? buffToHit : 0;
                        luckDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_MORALE:
                        moraleAttack = (buffToHit > moraleAttack ? buffToHit : moraleAttack);
                        moraleDamage = (buffToHit > moraleDamage ? buffDamage : moraleDamage);
                        moraleAttack += buffToHit < 0 ? buffToHit : 0;
                        moraleDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_PROFANE:
                        profaneAttack = (buffToHit > profaneAttack ? buffToHit : profaneAttack);
                        profaneDamage = (buffToHit > profaneDamage ? buffDamage : profaneDamage);
                        profaneAttack += buffToHit < 0 ? buffToHit : 0;
                        profaneDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_RACIAL:
                        racialAttack = (buffToHit > racialAttack ? buffToHit : racialAttack);
                        racialDamage = (buffToHit > racialDamage ? buffDamage : racialDamage);
                        racialAttack += buffToHit < 0 ? buffToHit : 0;
                        racialDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_SACRED:
                        sacredAttack = (buffToHit > sacredAttack ? buffToHit : sacredAttack);
                        sacredDamage = (buffToHit > sacredDamage ? buffDamage : sacredDamage);
                        sacredAttack += buffToHit < 0 ? buffToHit : 0;
                        sacredDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    case BUFF_TYPE_TRAIT:
                        traitAttack = (buffToHit > traitAttack ? buffToHit : traitAttack);
                        traitDamage = (buffToHit > traitDamage ? buffDamage : traitDamage);
                        traitAttack += buffToHit < 0 ? buffToHit : 0;
                        traitDamage += buffDamage < 0 ? buffDamage : 0;
                        break;
                    default:
                        untypedAttack += buffToHit;  //Untyped bonuses can stack
                        untypedDamage += buffDamage; //Untyped bonuses can stack
                        break;
                }

            }
        }

        fullBabAttackModifier += alchemicalAttack + circumstanceAttack + competenceAttack +
                enhancementAttack + inherentAttack + insightAttack + luckAttack + moraleAttack +
                profaneAttack + racialAttack + sacredAttack + traitAttack + untypedAttack;
        damage += alchemicalDamage + circumstanceDamage + competenceDamage + enhancementDamage +
                inherentDamage + insightDamage + luckDamage + moraleDamage + profaneDamage +
                racialDamage + sacredDamage + traitDamage+ untypedDamage;

        String attackRoutine = String.valueOf(fullBabAttackModifier);

        if (extraAttack) {
            attackRoutine += "/";
            attackRoutine += String.valueOf(fullBabAttackModifier);
        }
        if (character.getBab() >= 6) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 5);
        }
        if (character.getBab() >= 11) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 10);
        }
        if (character.getBab() >= 16) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 15);
        }

        String weaponDiceString = getWeaponDamageDiceString((increasesCreatureSize + increaseWeaponSize), primaryAttack.getWeaponDice());
        String baneDiceString = isGreaterBaneActive ? "4d6" : "2d6";
        String baneString = isBaneActive ? "+" + baneDiceString : "";

        return String.format("%s +%s (%s%s+%s %s%s%s)",
                primaryAttack.getName(), attackRoutine, weaponDiceString, baneString, damage,
                primaryAttack.getCritRange(),
                (TextUtils.isEmpty(primaryAttack.getCritRange()) ? "" : "/" ),
                primaryAttack.getCritMultiplier());
    }

    /**
     * Calculates to hit before buffs
     * @param character provides stats for calculations
     * @param attack provides stats for calculations
     * @return value of to hit before buffs
     */
    private int calculateFullBabAttackModifier(CharacterStatsModel character, AttackModel attack) {
        int output = 0;
        output += character.getBab();
        //Get strength or dexterity
        output += (attack.isFinesseable() ? character.getDexterityModifier() : character.getStrengthModifier());
        output += attack.getWeaponEchant();
        output += character.getMiscToHit();
        output += character.getSizeModifier();
        return output;
    }

    /**
     * Calculates damage before buffs
     * @param character provides stats for calculations
     * @param attack provides stats for calculations
     * @return damage before buffs
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
    private String getWeaponDamageDiceString(int numberOfSteps, String startingWeaponDamageDice) {
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
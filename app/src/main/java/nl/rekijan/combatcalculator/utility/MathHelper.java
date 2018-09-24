package nl.rekijan.combatcalculator.utility;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;
import nl.rekijan.combatcalculator.model.buffs.AbstractBuff;

import static java.util.Arrays.asList;

/**
 * Utility class for calculations<br>
 * Calculates ability modifiers from the ability score
 * Calculates attack rolls<br>
 * Calculates damage<br>
 * Calculates weapon dice<br>
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
            return (int) Math.floor(((abilityScore - 10) / (double) 2));
        } else {
            return (int) ((abilityScore - 10) / (double) 2);
        }
    }

    /**
     * Calculate the total to hit by calling both {@link MathHelper#calculateAttackModifier} and {@link MathHelper#calculateAttackModifiersFromBuffs}
     * @param buffs list of {@link AbstractBuff} to go through
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return value of to hit from all buffs
     */
    public int calculateTotalAttackModifier(List<AbstractBuff> buffs, CharacterStatsModel characterModel, AttackModel attackModel)
    {
        return calculateAttackModifier(characterModel, attackModel) + calculateAttackModifiersFromBuffs(buffs, characterModel, attackModel);
    }

    /**
     * Calculates to hit before buffs
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return value of to hit before buffs
     */
    private int calculateAttackModifier(CharacterStatsModel characterModel, AttackModel attackModel) {
        int output = 0;
        output += characterModel.getBab();
        //Get strength or dexterity
        output += (attackModel.isFinesseable() ? characterModel.getDexterityModifier() : characterModel.getStrengthModifier());
        output += attackModel.getWeaponEchant();
        output += characterModel.getMiscToHit();
        output += characterModel.getSizeModifier();
        return output;
    }

    /**
     * Calculate the to hit bonus from all the buffs
     * @param buffs list of {@link AbstractBuff} to go through
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return value of to hit from all buffs
     */
    private int calculateAttackModifiersFromBuffs(List<AbstractBuff> buffs, CharacterStatsModel characterModel, AttackModel attackModel)
    {
        int totalAttackModifierFromBuffs = 0;

        for (AbstractBuff buff : buffs)
        {
            if (buff.isActive())
            {
                buff.setCasterLevel(characterModel.getCasterLevel()); //TODO differentiate between buff's cl and character cl
                if (buff.getType() == BuffType.UNTYPED)
                {
                    totalAttackModifierFromBuffs += buff.calculateToHit(characterModel, attackModel);
                }
            }
        }

        for (BuffType buffT : BuffType.values())
        {
            if (buffT != BuffType.UNTYPED)
            {
                totalAttackModifierFromBuffs += searchHighestAttackRoll(buffs, buffT, characterModel, attackModel);
            }
        }

        return totalAttackModifierFromBuffs;
    }

    /**
     * Takes the buff list and filters out all non-active buffs as well as buffs not from the given BuffType. Then it returns only the highest value for the attack roll.
     * @param buffList list of {@link AbstractBuff} to go through
     * @param buffT {@link BuffType} to filter on
     * @param characterModel {@link CharacterStatsModel} to determine values
     * @param attack {@link AttackModel} to determine values
     * @return The highest attack roll value of the buffs, from that type and that is active.
     */
    private int searchHighestAttackRoll(List<AbstractBuff> buffList, final BuffType buffT, CharacterStatsModel characterModel, AttackModel attack)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            OptionalInt attackBonus = buffList.stream()
                    .filter(AbstractBuff::isActive)
                    .filter(p -> p.getType() == buffT)
                    .mapToInt(p -> p.calculateToHit(characterModel, attack))
                    .max();
            return attackBonus.isPresent() ? attackBonus.getAsInt() : 0;
        }
        else
        {
            int attackBonus = 0;
            for (AbstractBuff buff : buffList)
            {
              if (buff.isActive() && buff.getType() == buffT)
              {
                  int buffToHit = buff.calculateToHit(characterModel, attack);
                  if (buffToHit > attackBonus) attackBonus = buffToHit;
              }
            }
            return attackBonus;
        }
    }

    private List<String> weaponDiceList = new ArrayList<>(asList("1d2", "1d3", "1d4", "1d6",
            "1d8", "2d6", "3d6", "4d6", "6d6", "8d6", "12d6"));

    private List<String> altWeaponDiceList = new ArrayList<>(asList("1d10", "2d8", "3d8",
            "4d8", "6d8", "8d8", "12d8"));

    /**
     * Increases (or decreases) the weapon damage dice
     * @param buffs lists of Buffs to determine how many steps
     * @param startingWeaponDamageDice start value
     * @return new value of weapon damage dice
     */
    public String getWeaponDamageDiceString(List<AbstractBuff> buffs, String startingWeaponDamageDice) {
        int numberOfSteps = totalSizeIncreases(buffs);
        if (weaponDiceList.contains(startingWeaponDamageDice)) {
            int currentIndex = weaponDiceList.indexOf(startingWeaponDamageDice);
            return weaponDiceList.get(currentIndex + numberOfSteps);
        } else if (altWeaponDiceList.contains(startingWeaponDamageDice)) {
            int currentIndex = altWeaponDiceList.indexOf(startingWeaponDamageDice);
            return altWeaponDiceList.get(currentIndex + numberOfSteps);
        }
        return startingWeaponDamageDice;
    }

    /**
     * Calculates the number of size increase steps by calling both {@link MathHelper#searchHighestWeaponSizeIncrease} and {@link MathHelper#searchHighestCreatureSizeIncrease}
     * @param buffList list of {@link AbstractBuff} to go through
     * @return Total number of size increases
     */
    private int totalSizeIncreases(List<AbstractBuff> buffList)
    {
        return searchHighestWeaponSizeIncrease(buffList) + searchHighestCreatureSizeIncrease(buffList);
    }

    /**
     * Filters the buff lists and returns the highest weaponSizeIncrease of all the active buffs.
     * @param buffList list of {@link AbstractBuff} to go through
     * @return The highest weaponSizeIncrease of all the active buffs.
     */
    private int searchHighestWeaponSizeIncrease(List<AbstractBuff> buffList)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            OptionalInt weaponSizeIncrease = buffList.stream()
                    .filter(AbstractBuff::isActive)
                    .mapToInt(AbstractBuff::weaponSizeIncrease)
                    .max();
            return weaponSizeIncrease.isPresent() ? weaponSizeIncrease.getAsInt() : 0;
        }
        else
        {
            int weaponSizeIncrease = 0;
            for (AbstractBuff buff : buffList)
            {
                if (buff.isActive())
                {
                    if (buff.weaponSizeIncrease() > weaponSizeIncrease) weaponSizeIncrease = buff.weaponSizeIncrease();
                }
            }
            return weaponSizeIncrease;
        }
    }

    /**
     * Filters the buff lists and returns the highest creatureSizeIncrease of all the active buffs.
     * @param buffList list of {@link AbstractBuff} to go through
     * @return The highest creatureSizeIncrease of all the active buffs.
     */
    private int searchHighestCreatureSizeIncrease(List<AbstractBuff> buffList)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            OptionalInt creatureSizeIncrease = buffList.stream()
                    .filter(AbstractBuff::isActive)
                    .mapToInt(AbstractBuff::creatureSizeIncrease)
                    .max();
            return creatureSizeIncrease.isPresent() ? creatureSizeIncrease.getAsInt() : 0;
        }
        else
        {
            int creatureSizeIncrease = 0;
            for (AbstractBuff buff : buffList)
            {
                if (buff.isActive())
                {
                    if (buff.creatureSizeIncrease() > creatureSizeIncrease) creatureSizeIncrease = buff.creatureSizeIncrease();
                }
            }
            return creatureSizeIncrease;
        }
    }

    /**
     * Calculate the total damage by calling both {@link MathHelper#calculateDamage} and {@link MathHelper#calculateDamageFromBuffs}
     * @param buffs list of {@link AbstractBuff} to go through
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return damage from all the buffs
     */
    public int calculateTotalDamage(List<AbstractBuff> buffs, CharacterStatsModel characterModel, AttackModel attackModel)
    {
        return calculateDamage(characterModel, attackModel) + calculateDamageFromBuffs(buffs, characterModel, attackModel);
    }

    /**
     * Calculates damage before buffs
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return damage before buffs
     */
    private int calculateDamage(CharacterStatsModel characterModel, AttackModel attackModel) {
        int dmg = 0;

        int abilityModifier = characterModel.getStrengthModifier(); //TODO enable option for dex to damage
        if (attackModel.isTwoHandedWeapon()) {
            dmg += Math.floor(abilityModifier * 1.5);
        } else if (attackModel.isLigthWeapon()) {
            dmg += abilityModifier * 0.5;
        } else {
            dmg += abilityModifier;
        }

        dmg += attackModel.getWeaponEchant();
        dmg += characterModel.getMiscDamage();

        return dmg;
    }

    /**
     * Calculates damage from all the buffs
     * @param buffs list of {@link AbstractBuff} to go through
     * @param characterModel provides stats for calculations
     * @param attackModel provides stats for calculations
     * @return damage from all the buffs
     */
    private int calculateDamageFromBuffs(List<AbstractBuff> buffs, CharacterStatsModel characterModel, AttackModel attackModel)
    {
        int totalDamageFromBuffs = 0;

        for (AbstractBuff buff : buffs)
        {
            if (buff.isActive())
            {
                buff.setCasterLevel(characterModel.getCasterLevel()); //TODO differentiate between buff's cl and character cl
                if (buff.getType() == BuffType.UNTYPED)
                {
                    totalDamageFromBuffs += buff.calculateDamage(characterModel, attackModel);
                }
            }
        }

        for (BuffType buffT : BuffType.values())
        {
            if (buffT != BuffType.UNTYPED)
            {
                totalDamageFromBuffs += searchHighestDamage(buffs, buffT, characterModel, attackModel);
            }
        }

        return totalDamageFromBuffs;
    }

    /**
     * Takes the buff list and filters out all non-active buffs as well as buffs not from the given BuffType. Then it returns only the highest damage value.
     * @param buffList list of {@link AbstractBuff} to go through
     * @param buffT {@link BuffType} to filter on
     * @param characterModel {@link CharacterStatsModel} to determine values
     * @param attack {@link AttackModel} to determine values
     * @return The highest damage value of the buffs, from that type and that is active.
     */
    private int searchHighestDamage(List<AbstractBuff> buffList, final BuffType buffT, CharacterStatsModel characterModel, AttackModel attack)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            OptionalInt damage = buffList.stream()
                    .filter(AbstractBuff::isActive)
                    .filter(p -> p.getType() == buffT)
                    .mapToInt(p -> p.calculateDamage(characterModel, attack))
                    .max();
            return damage.isPresent() ? damage.getAsInt() : 0;
        }
        else
        {
            int damage = 0;
            for (AbstractBuff buff : buffList)
            {
                if (buff.isActive() && buff.getType() == buffT)
                {
                    int buffToHit = buff.calculateToHit(characterModel, attack);
                    if (buffToHit > damage) damage = buffToHit;
                }
            }
            return damage;
        }
    }
}
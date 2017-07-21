package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_LUCK;

/**
 * Logic of the Divine Power spells
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 21-7-2017
 */

public class DivinePower implements BuffInterface {

    private boolean isActive;
    private boolean isFatesFavored;
    private int casterLevel;

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        int toHit = casterLevel / 3;
        if (toHit < 1) toHit = 1;
        if (isFatesFavored) toHit++;
        return toHit;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        int dmg = casterLevel / 3;
        if (dmg < 1) dmg = 1;
        if (isFatesFavored) dmg++;
        return dmg;
    }

    @Override
    public String getName() {
        return "Divine Power";
    }

    @Override
    public String getType() {
        return BUFF_TYPE_LUCK;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setIsActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean grantsExtraAttack() {
        return true;
    }

    @Override
    public int creatureSizeIncrease() {
        return 0;
    }

    @Override
    public int weaponSizeIncrease() {
        return 0;
    }

    @Override
    public int getCasterLevel() {
        return casterLevel;
    }

    @Override
    public void setCasterLevel(int cl) {
        casterLevel = cl;
    }

    public boolean isFatesFavored() {
        return isFatesFavored;
    }

    public void setIsFatesFavored(boolean fatesFavored) {
        isFatesFavored = fatesFavored;
    }
}

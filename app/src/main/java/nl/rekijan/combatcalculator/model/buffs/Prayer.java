package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_LUCK;

/**
 * Logic for the spell Prayer
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 21-7-2017
 */

public class Prayer implements BuffInterface {

    private boolean isActive;
    private int casterLevel;
    private boolean isFatesFavored;

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return isFatesFavored ? 2 : 1;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return isFatesFavored ? 2 : 1;
    }

    @Override
    public String getName() {
        return "Prayer";
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
        return false;
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
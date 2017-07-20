package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

import static nl.rekijan.combatcalculator.AppConstants.BUFF_TYPE_ENHANCEMENT;

/**
 * Logic of the bane buff
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class Bane implements BuffInterface {

    private boolean isActive;
    private boolean isGreaterBaneActive;

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return 2;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return 2;
    }

    @Override
    public String getName() {
        return "Bane";
    }

    @Override
    public String getType() {
        return BUFF_TYPE_ENHANCEMENT;
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
        return 0;
    }

    @Override
    public void setCasterLevel(int casterLevel) {

    }

    public boolean isGreaterBaneActive(){
        return isGreaterBaneActive;
    }

    public void setIsGreaterBaneActive(boolean active) {
        isGreaterBaneActive = active;
    }
}
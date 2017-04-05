package nl.rekijan.geralt.model.buffs;

import nl.rekijan.geralt.model.AttackModel;
import nl.rekijan.geralt.model.CharacterStatsModel;

import static nl.rekijan.geralt.AppConstants.BUFF_TYPE_UNTYPED;

/**
 * Logic of PowerAttack
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 4-4-2017
 */

public class PowerAttack implements BuffInterface {

    private boolean isActive;
    private int casterLevel;

    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return (int) -(1 + Math.floor(character.getBab() / 4)); //Inverse because the to hit is a penalty
    }

    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        int steps = -calculateToHit(character, attack); //Inverse again because damage is positive
        int modifier = 2;
        if (attack.isTwoHandedWeapon()) modifier = 3;
        if (attack.isLigthWeapon()) modifier = 1;
        return steps * modifier;
    }

    @Override
    public String getType() {
        return BUFF_TYPE_UNTYPED;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

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
    public void setCasterLevel(int casterLevel) {
        this.casterLevel = casterLevel;
    }
}
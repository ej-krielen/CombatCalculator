package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;
import nl.rekijan.combatcalculator.AppConstants.BuffType;

/**
 * Logic of the bane buff
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class Bane extends AbstractBuff {

    private boolean isGreaterBaneActive;

    public Bane() {
        super("Bane", BuffType.ENHANCEMENT);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return 2;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return 2;
    }

    public boolean isGreaterBaneActive(){
        return isGreaterBaneActive;
    }

    public void setIsGreaterBaneActive(boolean active) {
        isGreaterBaneActive = active;
    }
}
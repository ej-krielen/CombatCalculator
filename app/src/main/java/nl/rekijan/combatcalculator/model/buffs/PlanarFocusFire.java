package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Logic of the Planar Focus Fire buff
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class PlanarFocusFire extends AbstractBuff {

    private boolean isPlanarFocusFireActive;

    public PlanarFocusFire() {
        super("Planar Focus - Fire", BuffType.UNTYPED);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return 0;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return 0;
    }

    public boolean isGreaterBaneActive(){
        return isPlanarFocusFireActive;
    }

    public void setIsGreaterBaneActive(boolean active) {
        isPlanarFocusFireActive = active;
    }
}
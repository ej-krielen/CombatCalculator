package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;


/**
 * Logic of flanking bonus
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class Flanking extends AbstractBuff {

    private boolean isOutflankActive;

    public Flanking() {
        super("Flanking", BuffType.UNTYPED);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return isOutflankActive ? 4 : 2;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return 0;
    }

    public void setIsOutflankActive(boolean active) {
        isOutflankActive = active;
    }
}
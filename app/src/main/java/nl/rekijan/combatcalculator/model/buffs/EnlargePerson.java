package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Logic of the Divine Power spells
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 21-7-2017
 */

public class EnlargePerson extends AbstractAbilityBuff {

    public EnlargePerson() {
        super("Enlarge Person", BuffType.SIZE, false, 1, 0, 2, -2);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return -1;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return 0;
    }
}
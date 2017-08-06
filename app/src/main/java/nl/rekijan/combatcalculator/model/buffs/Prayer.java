package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;


/**
 * Logic for the spell Prayer
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 21-7-2017
 */

public class Prayer extends AbstractBuff {

    public Prayer() {
        super("Prayer", BuffType.LUCK);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        return character.isFatesFavored() ? 2 : 1;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        return character.isFatesFavored() ? 2 : 1;
    }
}
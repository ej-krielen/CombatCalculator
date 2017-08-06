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

public class DivinePower extends AbstractBuff {

    public DivinePower() {
        super("Divine Power", BuffType.LUCK, true, 0, 0);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        int toHit = getCasterLevel() / 3;
        if (toHit < 1) toHit = 1;
        if (character.isFatesFavored()) toHit++;
        return toHit;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        int dmg = getCasterLevel() / 3;
        if (dmg < 1) dmg = 1;
        if (character.isFatesFavored()) dmg++;
        return dmg;
    }
}
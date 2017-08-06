package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Logic for the Divine Favor spell
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class DivineFavor extends AbstractBuff {

    public DivineFavor() {
        super("Divine Favor", BuffType.LUCK);
    }

    @Override
    public int calculateToHit(CharacterStatsModel character, AttackModel attack) {
        int toHit = getCasterLevel() / 3;
        if (toHit < 1) toHit = 1;
        if (toHit > 3) toHit = 3;
        if (character.isFatesFavored()) toHit++;
        return toHit;
    }

    @Override
    public int calculateDamage(CharacterStatsModel character, AttackModel attack) {
        int dmg = getCasterLevel() / 3;
        if (dmg < 1) dmg = 1;
        if (dmg > 3) dmg = 3;
        if (character.isFatesFavored()) dmg++;
        return dmg;
    }
}
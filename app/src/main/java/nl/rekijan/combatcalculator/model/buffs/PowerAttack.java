package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Logic of PowerAttack
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 4-4-2017
 */

public class PowerAttack extends AbstractBuff {

    public PowerAttack() {
        super("Power Attack", BuffType.UNTYPED, false, 0, 0);
    }

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
}
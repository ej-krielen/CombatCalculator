package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Abstract class for buffs that modify ability scores
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 1-10-2017
 */

public abstract class AbstractAbilityBuff extends AbstractBuff {

    private final int strenghtAdjustment;
    private final int dexterityAdjustment;

    public AbstractAbilityBuff(String name, AppConstants.BuffType type, int strenghtAdjustment, int dexterityAdjustment) {
        super(name, type);
        this.strenghtAdjustment = strenghtAdjustment;
        this.dexterityAdjustment = dexterityAdjustment;
    }

    public AbstractAbilityBuff(String name, AppConstants.BuffType type, boolean grantsExtraAttack, int creatureSizeIncrease, int weaponSizeIncrease , int strenghtAdjustment, int dexterityAdjustment) {
        super(name, type, grantsExtraAttack, creatureSizeIncrease, weaponSizeIncrease);
        this.strenghtAdjustment = strenghtAdjustment;
        this.dexterityAdjustment = dexterityAdjustment;
    }

    public abstract int calculateToHit(CharacterStatsModel character, AttackModel attack);
    public abstract int calculateDamage(CharacterStatsModel character, AttackModel attack);


    public int getStrenghtAdjustment() {
        return strenghtAdjustment;
    }

    public int getDexterityAdjustment() {
        return dexterityAdjustment;
    }
}
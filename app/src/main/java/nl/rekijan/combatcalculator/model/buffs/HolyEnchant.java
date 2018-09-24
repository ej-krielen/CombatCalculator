package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Logic of the holy weapon enchant
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 20-7-2017
 */

public class HolyEnchant extends AbstractBuff {

    private boolean isHolyEnchantActive;

    public HolyEnchant() {
        super("Holy Enchant", BuffType.UNTYPED);
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
        return isHolyEnchantActive;
    }

    public void setIsGreaterBaneActive(boolean active) {
        isHolyEnchantActive = active;
    }
}
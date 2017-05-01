package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Interface for all buffs
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 4-4-2017
 */

public interface BuffInterface {
    boolean isActive = false;
    int casterLevel = 0;

    int calculateToHit(CharacterStatsModel character, AttackModel attack);
    int calculateDamage(CharacterStatsModel character, AttackModel attack);

    String getName();
    String getType();
    boolean isActive();
    void setIsActive(boolean isActive);

    boolean grantsExtraAttack();

    int creatureSizeIncrease();
    int weaponSizeIncrease();

    int getCasterLevel();
    void setCasterLevel(int casterLevel);
}

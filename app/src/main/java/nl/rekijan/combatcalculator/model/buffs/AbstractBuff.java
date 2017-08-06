package nl.rekijan.combatcalculator.model.buffs;

import nl.rekijan.combatcalculator.AppConstants.BuffType;
import nl.rekijan.combatcalculator.model.AttackModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;

/**
 * Abstract class for all buffs
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 4-4-2017
 */

public abstract class AbstractBuff {
    private final String name;
    private final BuffType type;
    private final boolean grantsExtraAttack;
    private final int creatureSizeIncrease;
    private final int weaponSizeIncrease;

    private boolean isActive = false;
    private int casterLevel = 0;

    /**
     * Standard constructor
     * @param name String with name of the Buff
     * @param type BuffType used to prevent stacking
     */
    public AbstractBuff(String name, BuffType type) {
        this.name = name;
        this.type = type;
        grantsExtraAttack = false;
        creatureSizeIncrease = 0;
        weaponSizeIncrease = 0;
    }

    /**
     * Constructor for more advanced Buffs
     * @param name String with name of the Buff
     * @param type BuffType used to prevent stacking
     * @param grantsExtraAttack boolean, true if buff grants an extra attack
     * @param creatureSizeIncrease int of how many steps the buff increases the target creature in size
     * @param weaponSizeIncrease int of how many steps the buff increases the target weapon in size
     */
    public AbstractBuff(String name, BuffType type, boolean grantsExtraAttack, int creatureSizeIncrease, int weaponSizeIncrease)
    {
        this.name = name;
        this.type = type;
        this.grantsExtraAttack = grantsExtraAttack;
        this.creatureSizeIncrease = creatureSizeIncrease;
        this.weaponSizeIncrease = weaponSizeIncrease;
    }

    public abstract int calculateToHit(CharacterStatsModel character, AttackModel attack);
    public abstract int calculateDamage(CharacterStatsModel character, AttackModel attack);

    public String getName()
    {
        return name;
    }

    public BuffType getType()
    {
        return type;
    }

    public boolean grantsExtraAttack()
    {
        return grantsExtraAttack;
    }

    public int creatureSizeIncrease()
    {
        return creatureSizeIncrease;
    }
    public int weaponSizeIncrease()
    {
        return weaponSizeIncrease;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setIsActive(boolean active)
    {
        isActive = active;
    }

    public int getCasterLevel()
    {
        return casterLevel; //TODO differentiate between buff's value and characterModel
    }
    public void setCasterLevel(int cl)
    {
        casterLevel = cl;
    }
}
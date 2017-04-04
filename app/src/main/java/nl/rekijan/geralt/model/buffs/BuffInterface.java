package nl.rekijan.geralt.model.buffs;

import nl.rekijan.geralt.model.AttackModel;
import nl.rekijan.geralt.model.CharacterStatsModel;

/**
 * Interface for all buffs
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 4-4-2017
 */

public interface BuffInterface {
    boolean isActive = false;

    int calculateToHit(CharacterStatsModel character, AttackModel attack);
    int calculateDamage(CharacterStatsModel character, AttackModel attack);

    String getType();
    boolean isActive();
    void setIsActive(boolean isActive);
}

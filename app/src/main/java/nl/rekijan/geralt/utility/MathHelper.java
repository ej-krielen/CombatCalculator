package nl.rekijan.geralt.utility;

import nl.rekijan.geralt.model.CharacterStatsModel;

/**
 * Utility class for calculations
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 2-4-2017
 */

public class MathHelper {

    private static MathHelper sInstance = null;

    public static synchronized MathHelper getInstance() {
        if (sInstance == null) {
            sInstance = new MathHelper();
        }
        return sInstance;
    }

    public int abilityModifier(int abilityScore) {
        if (abilityScore < 10) {
            return (int) ((abilityScore - 10) / (double)2);
        } else {
            return (int) Math.floor(((abilityScore - 10) / (double)2));
        }
    }

    public String fullAttackString(CharacterStatsModel character) {
        String output = "+";
        int firstIterative = 0;
        firstIterative += character.getBab();
        //TODO choice between dex or str
        firstIterative += character.getStrengthModifier();
        firstIterative += character.getWeaponEnchant();
        firstIterative += character.getMiscToHit();
        output += String.valueOf(firstIterative);
        if (character.getBab() >= 6) {
            output += "/+";
            output += String.valueOf(firstIterative - 5);
        }
        //TODO check for hasted
        if (character.getBab() >= 11) {
            output += "/+";
            output += String.valueOf(firstIterative - 10);
        }
        if (character.getBab() >= 16) {
            output += "/+";
            output += String.valueOf(firstIterative - 15);
        }
        //TODO add real calculated damage
        output += " (2d6+16 19-20x2)"; //mock data for damage
        return output;
    }
}
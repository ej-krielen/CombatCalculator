package nl.rekijan.combatcalculator.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;

import nl.rekijan.combatcalculator.R;
import nl.rekijan.combatcalculator.model.buffs.AbstractBuff;
import nl.rekijan.combatcalculator.model.buffs.Bane;
import nl.rekijan.combatcalculator.utility.MathHelper;

/**
 * Class to construct the text to display the attack routine<br>
 * Example: Full attack: greatsword +13/+8 (2d6+8 19-20/x2)
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 30-7-2017
 */

public class AttackRoutineModel {

    private Context context;
    private CharacterStatsModel characterModel;
    private AttackModel attackModel;
    private List<AbstractBuff> buffs;

    public AttackRoutineModel (Context context, CharacterStatsModel characterModel, AttackModel attackModel, List<AbstractBuff> buffs)
    {
        this.context = context;
        this.characterModel = characterModel;
        this.attackModel = attackModel;
        this.buffs = buffs;
    }

    /**
     * Receive the updated information and set the TextView with {@link AttackRoutineModel#toString()}
     * @param characterModel {@link CharacterStatsModel} used for calculations
     * @param attackModel {@link AttackModel} used for calculations
     * @param buffs List of {@link AbstractBuff} used for calculations
     * @param fullAttackTextView TextView to fill with the string
     */
    public void updateAttackRoutineModel(CharacterStatsModel characterModel, AttackModel attackModel, List<AbstractBuff> buffs, TextView fullAttackTextView)
    {
        this.characterModel = characterModel;
        this.attackModel = attackModel;
        this.buffs = buffs;
        fullAttackTextView.setText(toString());
    }

    /**
     * Gathers pieces of data together to form a String<br>
     * Example: Full attack: greatsword +13/+8 (2d6+8 19-20/x2)
     *
     * @see #updateAttackRoutineModel
     * @see #getAttackRoutineString
     * @see MathHelper#getWeaponDamageDiceString
     * @see #getBaneString
     * @see #getTotalDamage
     *
     * @return the String to return
     */
    public String toString()
    {
        String fullAttackLabel = context.getString(R.string.full_attack_label);
        String criticalString = TextUtils.isEmpty(attackModel.getCritRange()) ? attackModel.getCritMultiplier() : attackModel.getCritRange() + "/" + attackModel.getCritMultiplier();

        return String.format("%s%s +%s (%s%s+%s %s)",
                fullAttackLabel, attackModel.getName(),
                getAttackRoutineString(),
                MathHelper.getInstance().getWeaponDamageDiceString(buffs, attackModel.getWeaponDice()),
                getBaneString(), getTotalDamage(), criticalString);
    }

    /**
     * Uses {@link MathHelper#calculateTotalAttackModifier} to get the highest attack modifier. Then see how many iterative and extra attacks to display.
     * Example: +13/+13/+8
     *
     * @see MathHelper#calculateTotalAttackModifier
     * @see #hasExtraAttack
     *
     * @return A String with the attack rolls
     */
    private String getAttackRoutineString()
    {
        //Calculate the total to hit bonus
        int fullBabAttackModifier = MathHelper.getInstance().calculateTotalAttackModifier(buffs, characterModel, attackModel);

        String attackRoutine = String.valueOf(fullBabAttackModifier);

        if (hasExtraAttack()) {
            attackRoutine += "/";
            attackRoutine += String.valueOf(fullBabAttackModifier);
        }
        if (characterModel.getBab() >= 6) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 5);
        }
        if (characterModel.getBab() >= 11) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 10);
        }
        if (characterModel.getBab() >= 16) {
            attackRoutine += "/+";
            attackRoutine += String.valueOf(fullBabAttackModifier - 15);
        }
        return attackRoutine;
    }

    /**
     * Goes through all buffs to see if you have an extra attack (from hasted effects)
     * @return true if at least one active buff grants extra attacks
     */
    private boolean hasExtraAttack()
    {
        for (AbstractBuff buff : buffs) {
            if (buff.isActive() && buff.grantsExtraAttack()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses {@link MathHelper#calculateTotalDamage} to the total damage value.
     * @return A String of the damage value
     */
    private String getTotalDamage()
    {
        return String.valueOf(MathHelper.getInstance().calculateTotalDamage(buffs, characterModel, attackModel));
    }

    /**
     * Goes through all buffs to see if bane is active and then checks if greater bane is active
     * @return A String with the value "+2d6" is bane is active, "+4d6" if both bane and greater bane are active, or an empty String.
     */
    private String getBaneString()
    {
        for (AbstractBuff buff : buffs)
        {
            if (buff.isActive())
            {
                if (buff.getName().equals("Bane"))
                {
                    return ((Bane) buff).isGreaterBaneActive() ? "+4d6" : "+2d6";
                }
            }
        }
        return "";
    }
}
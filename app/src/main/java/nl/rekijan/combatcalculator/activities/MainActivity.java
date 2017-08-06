package nl.rekijan.combatcalculator.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.rekijan.combatcalculator.R;
import nl.rekijan.combatcalculator.model.AttackRoutineModel;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;
import nl.rekijan.combatcalculator.model.buffs.AbstractBuff;
import nl.rekijan.combatcalculator.model.buffs.Bane;
import nl.rekijan.combatcalculator.model.buffs.DivineFavor;
import nl.rekijan.combatcalculator.model.buffs.DivinePower;
import nl.rekijan.combatcalculator.model.buffs.Flanking;
import nl.rekijan.combatcalculator.model.buffs.PowerAttack;
import nl.rekijan.combatcalculator.model.buffs.Prayer;
import nl.rekijan.combatcalculator.utility.dialogs.NumberPickerDialogFragment;

import static nl.rekijan.combatcalculator.AppConstants.DIALOG_BAB;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_CHARACTER_LEVEL;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_DEXTERITY;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_MISC_DAMAGE;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_MISC_TO_HIT;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_STRENGTH;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_TITLE;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_WEAPON_ENCHANT;
import static nl.rekijan.combatcalculator.AppConstants.MAX_VALUE_KEY;
import static nl.rekijan.combatcalculator.AppConstants.NUMBER_PICKER_VALUE;

public class MainActivity extends AppCompatActivity implements NumberPickerDialogFragment.NoticeDialogListener, View.OnClickListener {

    private List<AbstractBuff> buffList = new ArrayList<>();
    private CharacterStatsModel characterModel;
    private AttackRoutineModel attackRoutineModel;
    private TextView fullAttackTextView;
    private TextView characterLevelTextView;
    private TextView babTextView;
    private TextView strengthTextView;
    private TextView strengthModifierTextView;
    private TextView dexterityTextView;
    private TextView dexterityModifierTextView;
    private TextView weaponEnchantTextView;
    private TextView miscToHitTextView;
    private TextView miscDamageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        characterModel = new CharacterStatsModel(10, 10, 7, 18, 14, 0, 0, 0, false); //TODO replace with actual characterModel building
        characterModel.addAttack("greatsword", "2d6", 2, true, true, false, false, true, false, "19-20", "x2"); //TODO replace with actual attack creator
        final PowerAttack powerAttack = new PowerAttack();
        buffList.add(powerAttack);
        final Flanking flanking = new Flanking();
        buffList.add(flanking);
        final Bane bane = new Bane();
        buffList.add(bane);
        final DivineFavor divineFavor = new DivineFavor();
        buffList.add(divineFavor);
        final Prayer prayer = new Prayer();
        buffList.add(prayer);
        final DivinePower divinePower = new DivinePower();
        buffList.add(divinePower);
        attackRoutineModel = new AttackRoutineModel(this, characterModel, characterModel.getAttacks().get(0), buffList); //TODO selectable attack

        //Set all text views on startup
        fullAttackTextView = (TextView) findViewById(R.id.full_attack_textView);
        fullAttackTextView.setText(attackRoutineModel.toString());
        characterLevelTextView = (TextView) findViewById(R.id.character_level_editText);
        characterLevelTextView.setText(String.format("%s%s", getString(R.string.character_level_label), String.valueOf(characterModel.getCharacterLevel())));
        babTextView = (TextView) findViewById(R.id.bab_textView);
        babTextView.setText(String.format("%s%s", getString(R.string.bab_label), String.valueOf(characterModel.getBab())));
        strengthTextView = (TextView) findViewById(R.id.strength_textView);
        strengthTextView.setText(String.format("%s%s", getString(R.string.strength_label), String.valueOf(characterModel.getStrength())));
        strengthModifierTextView = (TextView) findViewById(R.id.strength_modifier_textView);
        strengthModifierTextView.setText(String.format("%s%s", getString(R.string.strength_modifier_label), String.valueOf(characterModel.getStrengthModifier())));
        dexterityTextView = (TextView) findViewById(R.id.dexterity_textView);
        dexterityTextView.setText(String.format("%s%s", getString(R.string.dexterity_label), String.valueOf(characterModel.getDexterity())));
        dexterityModifierTextView = (TextView) findViewById(R.id.dexterity_modifier_textView);
        dexterityModifierTextView.setText(String.format("%s%s", getString(R.string.dexterity_modifier_label), String.valueOf(characterModel.getDexterityModifier())));
        weaponEnchantTextView = (TextView) findViewById(R.id.weapon_enchant_textView);
        weaponEnchantTextView.setText(String.format("%s%s", getString(R.string.weapon_enchant_label), String.valueOf(characterModel.getAttacks().get(0).getWeaponEchant()))); //TODO hardcoded atm
        miscToHitTextView = (TextView) findViewById(R.id.misc_to_hit_textView);
        miscToHitTextView.setText(String.format("%s%s", getString(R.string.misc_to_hit_label), String.valueOf(characterModel.getMiscToHit())));
        miscDamageTextView = (TextView) findViewById(R.id.misc_damage_textView);
        miscDamageTextView.setText(String.format("%s%s", getString(R.string.misc_damage_label), String.valueOf(characterModel.getMiscDamage())));

        //Set listeners on the text views
        characterLevelTextView.setOnClickListener(this);
        babTextView.setOnClickListener(this);
        strengthTextView.setOnClickListener(this);
        dexterityTextView.setOnClickListener(this);
        weaponEnchantTextView.setOnClickListener(this);
        miscToHitTextView.setOnClickListener(this);
        miscDamageTextView.setOnClickListener(this);

        //Set check box and its listener
        final CheckedTextView flankingCheckBox = (CheckedTextView) findViewById(R.id.flanking_checkBox);
        flankingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flankingCheckBox.isChecked()) {
                    flankingCheckBox.setChecked(false);
                } else {
                    flankingCheckBox.setChecked(true);
                }
                flanking.setIsActive(flankingCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView outflankCheckBox = (CheckedTextView) findViewById(R.id.outflank_checkBox);
        outflankCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outflankCheckBox.isChecked()) {
                    outflankCheckBox.setChecked(false);
                } else {
                    outflankCheckBox.setChecked(true);
                }
                flanking.setIsOutflankActive(outflankCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView baneCheckBox = (CheckedTextView) findViewById(R.id.bane_checkBox);
        baneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (baneCheckBox.isChecked()) {
                    baneCheckBox.setChecked(false);
                } else {
                    baneCheckBox.setChecked(true);
                }
                bane.setIsActive(baneCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView greaterBaneCheckBox = (CheckedTextView) findViewById(R.id.greater_bane_checkBox);
        greaterBaneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (greaterBaneCheckBox.isChecked()) {
                    greaterBaneCheckBox.setChecked(false);
                } else {
                    greaterBaneCheckBox.setChecked(true);
                }
                bane.setIsGreaterBaneActive(greaterBaneCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView powerAttackCheckBox = (CheckedTextView) findViewById(R.id.power_attack_checkBox);
        powerAttackCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (powerAttackCheckBox.isChecked()) {
                    powerAttackCheckBox.setChecked(false);
                } else {
                    powerAttackCheckBox.setChecked(true);
                }
                powerAttack.setIsActive(powerAttackCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView divineFavorCheckBox = (CheckedTextView) findViewById(R.id.divine_favor_checkBox);
        divineFavorCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (divineFavorCheckBox.isChecked()) {
                    divineFavorCheckBox.setChecked(false);
                } else {
                    divineFavorCheckBox.setChecked(true);
                }
                divineFavor.setIsActive(divineFavorCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView fatesFavoredCheckBox = (CheckedTextView) findViewById(R.id.fates_favored_checkBox);
        fatesFavoredCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fatesFavoredCheckBox.isChecked()) {
                    fatesFavoredCheckBox.setChecked(false);
                } else {
                    fatesFavoredCheckBox.setChecked(true);
                }
                characterModel.setFatesFavored(fatesFavoredCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView prayerCheckBox = (CheckedTextView) findViewById(R.id.prayer_checkBox);
        prayerCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prayerCheckBox.isChecked()) {
                    prayerCheckBox.setChecked(false);
                } else {
                    prayerCheckBox.setChecked(true);
                }
                prayer.setIsActive(prayerCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

        //Set check box and its listener
        final CheckedTextView divinePowerCheckBox = (CheckedTextView) findViewById(R.id.divine_power_checkBox);
        divinePowerCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (divinePowerCheckBox.isChecked()) {
                    divinePowerCheckBox.setChecked(false);
                } else {
                    divinePowerCheckBox.setChecked(true);
                }
                divinePower.setIsActive(divinePowerCheckBox.isChecked());
                updateAttackRoutineModel();
            }
        });

    }

    /**
     * Updates all the info {@link AttackRoutineModel} needs to build the string and sets the TextView with it
     */
    public void updateAttackRoutineModel() {
        attackRoutineModel.updateAttackRoutineModel(characterModel, characterModel.getAttacks().get(0), buffList, fullAttackTextView);
    }

    public List<AbstractBuff> getBuffList() {
        return buffList;
    }

    public void setBuffList(ArrayList<AbstractBuff> buffList) {
        this.buffList = buffList;
    }

    /**
     * When dialog is closed with the positive button, use the value in a manner corresponding with the tag of the dialog.<br>
     *     Calls methods to update the field in the model and its text view, and recalculates the attack string.
     * @param dialog calling dialog
     * @param value value that was submitted
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int value) {
        updateCharacterModelAndView(dialog, value);
        updateAttackRoutineModel();
    }

    /**
     * Update the character model and the TexView
     * @param dialog calling dialog
     * @param value value that was submitted
     */
    private void updateCharacterModelAndView(DialogFragment dialog, int value) {
        switch (dialog.getTag()) {
            case DIALOG_CHARACTER_LEVEL:
                characterModel.setCharacterLevel(value);
                characterLevelTextView.setText(String.format("%s%s", getString(R.string.character_level_label), value));
                characterModel.setCasterLevel(value); //TODO remove later //TODO differentiate between buff's cl and character cl
                break;
            case DIALOG_BAB:
                characterModel.setBab(value);
                babTextView.setText(String.format("%s%s", getString(R.string.bab_label), value));
                break;
            case DIALOG_STRENGTH:
                characterModel.setStrength(value);
                strengthTextView.setText(String.format("%s%s", getString(R.string.strength_label), value));
                strengthModifierTextView.setText(String.format("%s%s", getString(R.string.strength_modifier_label), String.valueOf(characterModel.getStrengthModifier())));
                break;
            case DIALOG_DEXTERITY:
                characterModel.setDexterity(value);
                dexterityTextView.setText(String.format("%s%s", getString(R.string.dexterity_label), value));
                dexterityModifierTextView.setText(String.format("%s%s", getString(R.string.dexterity_modifier_label), String.valueOf(characterModel.getDexterityModifier())));
                break;
            case DIALOG_WEAPON_ENCHANT:
                characterModel.getAttacks().get(0).setWeaponEchant(value); //TODO hardcoded atm
                weaponEnchantTextView.setText(String.format("%s%s", getString(R.string.weapon_enchant_label), value));
                break;
            case DIALOG_MISC_TO_HIT:
                characterModel.setMiscToHit(value);
                miscToHitTextView.setText(String.format("%s%s", getString(R.string.misc_to_hit_label), value));
                break;
            case DIALOG_MISC_DAMAGE:
                characterModel.setMiscDamage(value);
                miscDamageTextView.setText(String.format("%s%s", getString(R.string.misc_damage_label), value));
                break;
            default:
                Toast.makeText(this, R.string.error_no_such_value + dialog.getTag(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {}

    /**
     * When a text view with a listener is called, check which text view is called and open a dialog with the correct information.
     * @param view to identify which view was clicked
     */
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        DialogFragment dialog = new NumberPickerDialogFragment();
        if (view == characterLevelTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getCharacterLevel());
            bundle.putInt(MAX_VALUE_KEY, 20);
            bundle.putString(DIALOG_TITLE, getString(R.string.character_level_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_CHARACTER_LEVEL);
        } else if (view == babTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getBab());
            bundle.putInt(MAX_VALUE_KEY, 20);
            bundle.putString(DIALOG_TITLE, getString(R.string.bab_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_BAB);
        } else if (view == strengthTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getStrength());
            bundle.putInt(MAX_VALUE_KEY, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.strength_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_STRENGTH);
        } else if (view == dexterityTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getDexterity());
            bundle.putInt(MAX_VALUE_KEY, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.dexterity_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_DEXTERITY);
        } else if (view == weaponEnchantTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getAttacks().get(0).getWeaponEchant()); //TODO hardcoded atm
            bundle.putInt(MAX_VALUE_KEY, 5);
            bundle.putString(DIALOG_TITLE, getString(R.string.weapon_enchant_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_WEAPON_ENCHANT);
        } else if (view == miscToHitTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getMiscToHit());
            bundle.putInt(MAX_VALUE_KEY, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.misc_to_hit_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_MISC_TO_HIT);
        } else if (view == miscDamageTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, characterModel.getMiscDamage());
            bundle.putInt(MAX_VALUE_KEY, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.misc_damage_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_MISC_DAMAGE);
        }
    }
}
package nl.rekijan.combatcalculator.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.rekijan.combatcalculator.R;
import nl.rekijan.combatcalculator.model.CharacterStatsModel;
import nl.rekijan.combatcalculator.model.buffs.BuffInterface;
import nl.rekijan.combatcalculator.model.buffs.PowerAttack;
import nl.rekijan.combatcalculator.utility.MathHelper;
import nl.rekijan.combatcalculator.utility.dialogs.NumberPickerDialogFragment;

import static nl.rekijan.combatcalculator.AppConstants.DIALOG_BAB;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_CHARACTER_LEVEL;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_DEXTERITY;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_MISC_DAMAGE;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_MISC_TO_HIT;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_STRENGTH;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_TITLE;
import static nl.rekijan.combatcalculator.AppConstants.DIALOG_WEAPON_ENCHANT;
import static nl.rekijan.combatcalculator.AppConstants.MAX_VALUE;
import static nl.rekijan.combatcalculator.AppConstants.NUMBER_PICKER_VALUE;

public class MainActivity extends AppCompatActivity implements NumberPickerDialogFragment.NoticeDialogListener, View.OnClickListener {

    private ArrayList<BuffInterface> buffList = new ArrayList<>();
    private CharacterStatsModel character;
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

        character = new CharacterStatsModel(10, 10, 7, 18, 14, 0, 0, 0); //TODO replace with actual character building
        character.addAttack("greatsword", "2d6", 2, true, true, false, false, true, false, "19-20", "x2"); //TODO replace with actual attack creator
        final PowerAttack powerAttack = new PowerAttack();
        buffList.add(powerAttack);

        //Set all text views on startup
        fullAttackTextView = (TextView) findViewById(R.id.full_attack_textView);
        calculate(character, fullAttackTextView);
        characterLevelTextView = (TextView) findViewById(R.id.character_level_editText);
        characterLevelTextView.setText(String.format("%s%s", getString(R.string.character_level_label), String.valueOf(character.getCharacterLevel())));
        babTextView = (TextView) findViewById(R.id.bab_textView);
        babTextView.setText(String.format("%s%s", getString(R.string.bab_label), String.valueOf(character.getBab())));
        strengthTextView = (TextView) findViewById(R.id.strength_textView);
        strengthTextView.setText(String.format("%s%s", getString(R.string.strength_label), String.valueOf(character.getStrength())));
        strengthModifierTextView = (TextView) findViewById(R.id.strength_modifier_textView);
        strengthModifierTextView.setText(String.format("%s%s", getString(R.string.strength_modifier_label), String.valueOf(character.getStrengthModifier())));
        dexterityTextView = (TextView) findViewById(R.id.dexterity_textView);
        dexterityTextView.setText(String.format("%s%s", getString(R.string.dexterity_label), String.valueOf(character.getDexterity())));
        dexterityModifierTextView = (TextView) findViewById(R.id.dexterity_modifier_textView);
        dexterityModifierTextView.setText(String.format("%s%s", getString(R.string.dexterity_modifier_label), String.valueOf(character.getDexterityModifier())));
        weaponEnchantTextView = (TextView) findViewById(R.id.weapon_enchant_textView);
        weaponEnchantTextView.setText(String.format("%s%s", getString(R.string.weapon_enchant_label), String.valueOf(character.getAttacks().get(0).getWeaponEchant()))); //TODO hardcoded atm
        miscToHitTextView = (TextView) findViewById(R.id.misc_to_hit_textView);
        miscToHitTextView.setText(String.format("%s%s", getString(R.string.misc_to_hit_label), String.valueOf(character.getMiscToHit())));
        miscDamageTextView = (TextView) findViewById(R.id.misc_damage_textView);
        miscDamageTextView.setText(String.format("%s%s", getString(R.string.misc_damage_label), String.valueOf(character.getMiscDamage())));

        //Set listeners on the text views
        characterLevelTextView.setOnClickListener(this);
        babTextView.setOnClickListener(this);
        strengthTextView.setOnClickListener(this);
        dexterityTextView.setOnClickListener(this);
        weaponEnchantTextView.setOnClickListener(this);
        miscToHitTextView.setOnClickListener(this);
        miscDamageTextView.setOnClickListener(this);

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
                calculate(character, fullAttackTextView);
            }
        });
    }

    /**
     * Uses {@linkplain MathHelper#fullAttackString(ArrayList, CharacterStatsModel) a method in helper class} to make a string based on the stats of the paramater
     * @param character use this model's stats
     * @param fullAttackTextView text view to set text into
     */
    public void calculate(CharacterStatsModel character, TextView fullAttackTextView) {
        String fullAttackString = getString(R.string.full_attack_label) +
                MathHelper.getInstance().fullAttackString(buffList, character);
        fullAttackTextView.setText(fullAttackString);
    }

    public ArrayList<BuffInterface> getBuffList() {
        return buffList;
    }

    public void setBuffList(ArrayList<BuffInterface> buffList) {
        this.buffList = buffList;
    }

    /**
     * When dialog is closed with the positive button, use the value in a manner corresponding with the tag of the dialog.<br>
     *     Usually updating the field in the model and the text view.
     * @param dialog calling dialog
     * @param value value that was submitted
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int value) {
        switch (dialog.getTag()) {
            case DIALOG_CHARACTER_LEVEL:
                character.setCharacterLevel(value);
                characterLevelTextView.setText(String.format("%s%s", getString(R.string.character_level_label), value));
                break;
            case DIALOG_BAB:
                character.setBab(value);
                babTextView.setText(String.format("%s%s", getString(R.string.bab_label), value));
                break;
            case DIALOG_STRENGTH:
                character.setStrength(value);
                strengthTextView.setText(String.format("%s%s", getString(R.string.strength_label), value));
                strengthModifierTextView.setText(String.format("%s%s", getString(R.string.strength_modifier_label), String.valueOf(character.getStrengthModifier())));
                break;
            case DIALOG_DEXTERITY:
                character.setDexterity(value);
                dexterityTextView.setText(String.format("%s%s", getString(R.string.dexterity_label), value));
                dexterityModifierTextView.setText(String.format("%s%s", getString(R.string.dexterity_modifier_label), String.valueOf(character.getDexterityModifier())));
                break;
            case DIALOG_WEAPON_ENCHANT:
                character.getAttacks().get(0).setWeaponEchant(value); //TODO hardcoded atm
                weaponEnchantTextView.setText(String.format("%s%s", getString(R.string.weapon_enchant_label), value));
                break;
            case DIALOG_MISC_TO_HIT:
                character.setMiscToHit(value);
                miscToHitTextView.setText(String.format("%s%s", getString(R.string.misc_to_hit_label), value));
                break;
            case DIALOG_MISC_DAMAGE:
                character.setMiscDamage(value);
                miscDamageTextView.setText(String.format("%s%s", getString(R.string.misc_damage_label), value));
                break;
            default:
                Toast.makeText(this, R.string.error_no_such_value + dialog.getTag(), Toast.LENGTH_LONG).show();
                break;
        }
        calculate(character, fullAttackTextView);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    /**
     * When a text view with a listener is called, check which text view is called and open a dialog with the correct information.
     * @param view to identify which view was clicked
     */
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        DialogFragment dialog = new NumberPickerDialogFragment();
        if (view == characterLevelTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getCharacterLevel());
            bundle.putInt(MAX_VALUE, 20);
            bundle.putString(DIALOG_TITLE, getString(R.string.character_level_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_CHARACTER_LEVEL);
        } else if (view == babTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getBab());
            bundle.putInt(MAX_VALUE, 20);
            bundle.putString(DIALOG_TITLE, getString(R.string.bab_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_BAB);
        } else if (view == strengthTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getStrength());
            bundle.putInt(MAX_VALUE, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.strength_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_STRENGTH);
        } else if (view == dexterityTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getDexterity());
            bundle.putInt(MAX_VALUE, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.dexterity_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_DEXTERITY);
        } else if (view == weaponEnchantTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getAttacks().get(0).getWeaponEchant()); //TODO hardcoded atm
            bundle.putInt(MAX_VALUE, 5);
            bundle.putString(DIALOG_TITLE, getString(R.string.weapon_enchant_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_WEAPON_ENCHANT);
        } else if (view == miscToHitTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getMiscToHit());
            bundle.putInt(MAX_VALUE, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.misc_to_hit_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_MISC_TO_HIT);
        } else if (view == miscDamageTextView) {
            bundle.putInt(NUMBER_PICKER_VALUE, character.getMiscDamage());
            bundle.putInt(MAX_VALUE, 100);
            bundle.putString(DIALOG_TITLE, getString(R.string.misc_damage_dialog_title));
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), DIALOG_MISC_DAMAGE);
        }
    }
}
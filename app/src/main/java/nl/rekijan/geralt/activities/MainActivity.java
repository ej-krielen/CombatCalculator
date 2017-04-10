package nl.rekijan.geralt.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import nl.rekijan.geralt.R;
import nl.rekijan.geralt.model.CharacterStatsModel;
import nl.rekijan.geralt.model.buffs.BuffInterface;
import nl.rekijan.geralt.model.buffs.PowerAttack;
import nl.rekijan.geralt.utility.MathHelper;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BuffInterface> buffList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CharacterStatsModel character = new CharacterStatsModel(10, 10, 7, 18, 14, 0, 0, 0);
        character.addAttack("greatsword", "2d6", 2, true, true, false, false, true, false, "19-20", "x2");
        final PowerAttack powerAttack = new PowerAttack();
        buffList.add(powerAttack);

        final TextView fullAttackTextView = (TextView) findViewById(R.id.full_attack_textView);
        calculate(character, fullAttackTextView);
        final EditText characterLevelTextView = (EditText) findViewById(R.id.character_level_editText);
        characterLevelTextView.setText(String.valueOf(character.getCharacterLevel()));
        final EditText babTextView = (EditText) findViewById(R.id.bab_textView);
        babTextView.setText(String.valueOf(character.getBab()));
        final EditText strengthTextView = (EditText) findViewById(R.id.strength_textView);
        strengthTextView.setText(String.valueOf(character.getStrength()));
        final TextView strengthModifierTextView = (TextView) findViewById(R.id.strength_modifier_textView);
        strengthModifierTextView.setText(String.valueOf(character.getStrengthModifier()));
        final EditText dexterityTextView = (EditText) findViewById(R.id.dexterity_textView);
        dexterityTextView.setText(String.valueOf(character.getDexterity()));
        final TextView dexterityModifierTextView = (TextView) findViewById(R.id.dexterity_modifier_textView);
        dexterityModifierTextView.setText(String.valueOf(character.getDexterityModifier()));
        final EditText weaponEnchantTextView = (EditText) findViewById(R.id.weapon_enchant_textView);
        weaponEnchantTextView.setText(String.valueOf(character.getAttacks().get(0).getWeaponEchant()));
        final EditText miscToHitTextView = (EditText) findViewById(R.id.misc_to_hit_textView);
        miscToHitTextView.setText(String.valueOf(character.getMiscToHit()));
        final EditText miscDamageTextView = (EditText) findViewById(R.id.misc_damage_textView);
        miscDamageTextView.setText(String.valueOf(character.getMiscDamage()));

        final CheckBox powerAttackCheckBox = (CheckBox) findViewById(R.id.power_attack_checkBox);

        Button calculateButton = (Button) findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(characterLevelTextView.getText().toString()) != character.getCharacterLevel()) {
                    character.setCharacterLevel(Integer.valueOf(characterLevelTextView.getText().toString()));
                }
                if (Integer.valueOf(babTextView.getText().toString()) != character.getBab()) {
                    character.setBab(Integer.valueOf(babTextView.getText().toString()));
                }
                if (Integer.valueOf(strengthTextView.getText().toString()) != character.getStrength()) {
                    character.setStrength(Integer.valueOf(strengthTextView.getText().toString()));
                    strengthModifierTextView.setText(String.valueOf(character.getStrengthModifier()));
                }
                if (Integer.valueOf(dexterityTextView.getText().toString()) != character.getDexterity()) {
                    character.setDexterity(Integer.valueOf(dexterityTextView.getText().toString()));
                    dexterityModifierTextView.setText(String.valueOf(character.getDexterityModifier()));
                }
                if (Integer.valueOf(weaponEnchantTextView.getText().toString()) != character.getAttacks().get(0).getWeaponEchant()) {
                    character.getAttacks().get(0).setWeaponEchant(Integer.valueOf(weaponEnchantTextView.getText().toString()));
                }
                if (Integer.valueOf(miscToHitTextView.getText().toString()) != character.getMiscToHit()) {
                    character.setMiscToHit(Integer.valueOf(miscToHitTextView.getText().toString()));
                }
                if (Integer.valueOf(miscDamageTextView.getText().toString()) != character.getMiscDamage()) {
                    character.setMiscDamage(Integer.valueOf(miscDamageTextView.getText().toString()));
                }
                powerAttack.setIsActive(powerAttackCheckBox.isChecked());
                calculate(character, fullAttackTextView);
            }
        });

        powerAttackCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                powerAttack.setIsActive(powerAttackCheckBox.isChecked());
                calculate(character, fullAttackTextView);
            }
        });

        babTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    if (TextUtils.isEmpty(babTextView.getText())) {
                        babTextView.setText(String.valueOf(character.getBab()));
                    } else if (Integer.valueOf(babTextView.getText().toString()) != character.getBab()) {
                        character.setBab(Integer.valueOf(babTextView.getText().toString()));
                        calculate(character, fullAttackTextView);
                    }

                } else {
                    babTextView.setText("");
                    InputMethodManager keyboard = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(babTextView, 0);
                }
            }
        });

        babTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(babTextView.getText())) {
                    if (Integer.valueOf(babTextView.getText().toString()) != character.getBab()) {
                        character.setBab(Integer.valueOf(babTextView.getText().toString()));
                        calculate(character, fullAttackTextView);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


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
}
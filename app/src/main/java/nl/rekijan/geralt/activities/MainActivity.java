package nl.rekijan.geralt.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

        final CharacterStatsModel geralt = new CharacterStatsModel(10, 10, 7, 18, 14, 0, 0, 0);
        geralt.addAttack("greatsword", "2d6", 2, true, true, false, false, true, false, "19-20", "x2");
        final PowerAttack powerAttack = new PowerAttack();
        buffList.add(powerAttack);

        final TextView fullAttackTextView = (TextView) findViewById(R.id.full_attack_textView);
        String fullAttackString = getString(R.string.full_attack_label) + MathHelper.getInstance().fullAttackString(buffList, geralt);
        fullAttackTextView.setText(fullAttackString);
        EditText characterLevelTextView = (EditText) findViewById(R.id.character_level_editText);
        characterLevelTextView.setText(String.valueOf(geralt.getCharacterLevel()));
        final EditText babTextView = (EditText) findViewById(R.id.bab_textView);
        babTextView.setText(String.valueOf(geralt.getBab()));
        EditText strengthTextView = (EditText) findViewById(R.id.strength_textView);
        strengthTextView.setText(String.valueOf(geralt.getStrength()));
        TextView strengthModifierTextView = (TextView) findViewById(R.id.strength_modifier_textView);
        strengthModifierTextView.setText(String.valueOf(geralt.getStrengthModifier()));
        EditText dexterityTextView = (EditText) findViewById(R.id.dexterity_textView);
        dexterityTextView.setText(String.valueOf(geralt.getDexterity()));
        TextView dexterityModifierTextView = (TextView) findViewById(R.id.dexterity_modifier_textView);
        dexterityModifierTextView.setText(String.valueOf(geralt.getDexterityModifier()));
        EditText weaponEnchantTextView = (EditText) findViewById(R.id.weapon_enchant_textView);
        weaponEnchantTextView.setText(String.valueOf(geralt.getAttacks().get(0).getWeaponEchant()));
        EditText miscToHitTextView = (EditText) findViewById(R.id.misc_to_hit_textView);
        miscToHitTextView.setText(String.valueOf(geralt.getMiscToHit()));
        EditText miscDamageTextView = (EditText) findViewById(R.id.misc_damage_textView);
        miscDamageTextView.setText(String.valueOf(geralt.getMiscDamage()));

        final CheckBox powerAttackCheckBox = (CheckBox) findViewById(R.id.power_attack_checkBox);

        Button calculateButton = (Button) findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(babTextView.getText().toString()) != geralt.getBab()) {
                    geralt.setBab(Integer.valueOf(babTextView.getText().toString()));
                }
                powerAttack.setIsActive(powerAttackCheckBox.isChecked());
                String fullAttackString = getString(R.string.full_attack_label) + MathHelper.getInstance().fullAttackString(buffList, geralt);
                fullAttackTextView.setText(fullAttackString);
            }
        });
    }

    public ArrayList<BuffInterface> getBuffList() {
        return buffList;
    }

    public void setBuffList(ArrayList<BuffInterface> buffList) {
        this.buffList = buffList;
    }
}
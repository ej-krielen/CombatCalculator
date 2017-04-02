package nl.rekijan.geralt.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import nl.rekijan.geralt.R;
import nl.rekijan.geralt.model.CharacterStatsModel;
import nl.rekijan.geralt.utility.MathHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharacterStatsModel geralt = new CharacterStatsModel();
        geralt.setCharacterLevel(10);
        geralt.setBab(7);
        geralt.setStrength(16);
        geralt.setDexterity(14);
        geralt.setWeaponEnchant(2);
        geralt.setMiscToHit(0);
        geralt.setMiscDamage(0);

        TextView fullAttackTextView = (TextView) findViewById(R.id.full_attack_textView);
        fullAttackTextView.setText(MathHelper.getInstance().fullAttackString(geralt));
        TextView characterLevelTextView = (TextView) findViewById(R.id.character_level_textView);
        characterLevelTextView.setText(String.valueOf(geralt.getCharacterLevel()));
        TextView babTextView = (TextView) findViewById(R.id.bab_textView);
        babTextView.setText(String.valueOf(geralt.getBab()));
        TextView strengthTextView = (TextView) findViewById(R.id.strength_textView);
        strengthTextView.setText(String.valueOf(geralt.getStrength()));
        TextView strengthModifierTextView = (TextView) findViewById(R.id.strength_modifier_textView);
        strengthModifierTextView.setText(String.valueOf(geralt.getStrengthModifier()));
        TextView dexterityTextView = (TextView) findViewById(R.id.dexterity_textView);
        dexterityTextView.setText(String.valueOf(geralt.getDexterity()));
        TextView dexterityModifierTextView = (TextView) findViewById(R.id.dexterity_modifier_textView);
        dexterityModifierTextView.setText(String.valueOf(geralt.getDexterityModifier()));
        TextView weaponEnchantTextView = (TextView) findViewById(R.id.weapon_enchant_textView);
        weaponEnchantTextView.setText(String.valueOf(geralt.getWeaponEnchant()));
        TextView miscToHitTextView = (TextView) findViewById(R.id.misc_to_hit_textView);
        miscToHitTextView.setText(String.valueOf(geralt.getMiscToHit()));
        TextView miscDamageTextView = (TextView) findViewById(R.id.misc_damage_textView);
        miscDamageTextView.setText(String.valueOf(geralt.getMiscDamage()));
    }
}
package nl.rekijan.geralt.model;

/**
 * Model for all (weapon and natural) attacks
 *
 * @author Erik-Jan Krielen ej.krielen@gmail.com
 * @since 3-4-2017
 */

public class AttackModel {

    private String name;
    private String weaponDice;
    private int weaponEchant;
    private boolean isMelee;
    private boolean isManufacturedWeapon;
    private boolean isThrown;
    private boolean isFinesseable;
    private boolean isTwoHandedWeapon;
    private boolean isLigthWeapon;

    private String critRange;
    private String critMultiplier;

    public AttackModel(String name, String weaponDice, int weaponEchant, boolean isMelee,
                       boolean isManufacturedWeapon, boolean isThrown, boolean isFinesseable,
                       boolean isTwoHandedWeapon, boolean isLigthWeapon, String critRange,
                       String critMultiplier) {

        this.name = name;
        this.weaponDice = weaponDice;
        this.weaponEchant = weaponEchant;
        this.isMelee = isMelee;
        this.isManufacturedWeapon = isManufacturedWeapon;
        this.isThrown = isThrown;
        this.isFinesseable = isFinesseable;
        this.isTwoHandedWeapon = isTwoHandedWeapon;
        this.isLigthWeapon = isLigthWeapon;
        this.critRange = critRange;
        this.critMultiplier = critMultiplier;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeaponDice() {
        return weaponDice;
    }

    public void setWeaponDice(String weaponDice) {
        this.weaponDice = weaponDice;
    }

    public int getWeaponEchant() {
        return weaponEchant;
    }

    public void setWeaponEchant(int weaponEchant) {
        this.weaponEchant = weaponEchant;
    }

    public boolean isMelee() {
        return isMelee;
    }

    public void setMelee(boolean melee) {
        isMelee = melee;
    }

    public boolean isManufacturedWeapon() {
        return isManufacturedWeapon;
    }

    public void setManufacturedWeapon(boolean manufacturedWeapon) {
        isManufacturedWeapon = manufacturedWeapon;
    }

    public boolean isThrown() {
        return isThrown;
    }

    public void setThrown(boolean thrown) {
        isThrown = thrown;
    }

    public boolean isFinesseable() {
        return isFinesseable;
    }

    public void setFinesseable(boolean finesseable) {
        isFinesseable = finesseable;
    }

    public boolean isTwoHandedWeapon() {
        return isTwoHandedWeapon;
    }

    public void setTwoHandedWeapon(boolean twoHandedWeapon) {
        isTwoHandedWeapon = twoHandedWeapon;
    }

    public boolean isLigthWeapon() {
        return isLigthWeapon;
    }

    public void setLigthWeapon(boolean ligthWeapon) {
        isLigthWeapon = ligthWeapon;
    }

    public String getCritRange() {
        return critRange;
    }

    public void setCritRange(String critRange) {
        this.critRange = critRange;
    }

    public String getCritMultiplier() {
        return critMultiplier;
    }

    public void setCritMultiplier(String critMultiplier) {
        this.critMultiplier = critMultiplier;
    }
}
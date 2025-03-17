public class Player {
    private int health = 10;
    private int weapon = 0;
    private int lastKill = 100;
    private boolean skipAvail = true;

    public Player() { //int health, int weapon, int lastKill, boolean skipAvail) {
    }

    boolean isDead() {
        return this.health <= 0;
    }

    boolean isSkipAvail() {
        return skipAvail;
    }

    int getHealth() {
        return this.health;
    }

    int getWeapon() {
        return this.weapon;
    }

    void fight(int damageValue) {
        if (damageValue > this.weapon) {
            this.health = Math.max(0, this.health - (damageValue - this.weapon));
        }
    }

    void takeDamage(int damageValue) {
        this.health = Math.max(0, this.health - damageValue);
    }

    void heal(int healValue) {
        this.health = Math.min(20, this.health + healValue);
    }

    void equip(int weapon) {
        this.weapon = weapon;
        this.lastKill = 100;
    }

    int getLastKill() {
        return lastKill;
    }

    void setLastKill(int newLastKill){
        this.lastKill = newLastKill;
    }

    void skippedHand() {
        this.skipAvail = false;
    }

    void skipReset() {
        this.skipAvail = true;
    }


}

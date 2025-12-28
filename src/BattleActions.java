public interface BattleActions {
    void basicAttack(Monster target);
    void specialAttack(Monster target);
    void elementalAttack(Monster target);
    void useItem(Item item, Monster target);
    boolean flee();
}
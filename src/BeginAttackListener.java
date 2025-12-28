public interface BeginAttackListener {
  public String attack(BeginAttackLayout layout, int action);
  void heal(BeginAttackLayout layout);
  void backToDungeon();
  void elemntalApply(BeginAttackLayout layout, int code);
}
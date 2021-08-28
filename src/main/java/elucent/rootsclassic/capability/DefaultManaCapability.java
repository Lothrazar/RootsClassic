package elucent.rootsclassic.capability;

import java.util.concurrent.Callable;

public class DefaultManaCapability implements Callable<IManaCapability> {

  @Override
  public IManaCapability call() throws Exception {
    return new IManaCapability() {

      public float maxMana = 40;
      public float mana = 40;

      @Override
      public float getMana() {
        return mana;
      }

      @Override
      public float getMaxMana() {
        return maxMana;
      }

      @Override
      public void setMana(float mana) {
        this.mana = mana;
        if (mana < 0) {
          this.mana = 0;
        }
        if (mana > getMaxMana()) {
          this.mana = getMaxMana();
        }
      }

      @Override
      public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
      }
    };
  }
}

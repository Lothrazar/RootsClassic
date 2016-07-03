package elucent.roots.capability;

public class DefaultManaCapability implements IManaCapability {
	public float maxMana = 0;
	public float mana = 0;
	public DefaultManaCapability(float maxMana, float mana){
		this.maxMana = maxMana;
		this.mana = mana;
	}
	
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
		if (mana < 0){
			this.mana = 0;
		}
		if (mana > getMaxMana()){
			this.mana = getMaxMana();
		}
	}

	@Override
	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}
}

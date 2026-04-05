package elucent.rootsclassic.attachment;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

public class ManaAttachment implements IMana, IAttachmentSerializer<ManaAttachment> {

  private float maxMana = 40;
  private float mana = 40;

  public ManaAttachment() {
    this.maxMana = 40;
    this.mana = 40;
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

  @Override
  public ManaAttachment read(IAttachmentHolder holder, ValueInput input) {
    ManaAttachment attachment = new ManaAttachment();

    attachment.setMana(input.getFloatOr("mana", 40));
    attachment.setMaxMana(input.getFloatOr("maxMana", 40));

    return attachment;
  }

  @Override
  public boolean write(ManaAttachment attachment, ValueOutput output) {
    output.putFloat("mana", attachment.getMana());
    output.putFloat("maxMana", attachment.getMaxMana());
    return true;
  }
}

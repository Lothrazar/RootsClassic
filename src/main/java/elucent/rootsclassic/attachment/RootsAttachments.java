package elucent.rootsclassic.attachment;

import elucent.rootsclassic.Const;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RootsAttachments {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Const.MODID);

	public static final Supplier<AttachmentType<ManaAttachment>> MANA = ATTACHMENT_TYPES.register("mana", () ->
		AttachmentType.builder(ManaAttachment::new).build());
}

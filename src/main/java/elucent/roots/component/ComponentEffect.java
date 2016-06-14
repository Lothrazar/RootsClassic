package elucent.roots.component;

import net.minecraft.entity.player.EntityPlayer;

public class ComponentEffect {
	public String name;
	public int duration;
	public EntityPlayer player;
	
	public ComponentEffect(String name, int duration, EntityPlayer player){
		this.name = name;
		this.duration = duration;
		this.player = player;
	}
}

package elucent.rootsclassic.entity;

import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAccelerator extends Entity {
	Entity entity;
	Random random = new Random();
	int lifetime = 0;
	int potency = 1;
	public EntityAccelerator(World world, Entity entity, int potency, int size) {
		super(world);
		this.entity = entity;
		this.potency = potency+2;
		this.lifetime = 200+200*size;
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		this.posX = (entity.getEntityBoundingBox().maxX+entity.getEntityBoundingBox().minX)/2.0-0.5;
		this.posY = (entity.getEntityBoundingBox().maxY+entity.getEntityBoundingBox().minY)/2.0-0.5;
		this.posZ = (entity.getEntityBoundingBox().maxZ+entity.getEntityBoundingBox().minZ)/2.0-0.5;
		if (entity == null){
			this.kill();
			this.getEntityWorld().removeEntity(this);
		}
		else {
			for (int i = 0; i < potency; i ++){
				entity.onUpdate();
				entity.onEntityUpdate();
			}
		}
		for (int i = 0; i < 2; i ++){
			int side = random.nextInt(6);
			if (side == 0){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX, posY+random.nextDouble(), posZ+random.nextDouble(), 0, 0, 0, 255, 255, 255);
			}
			if (side == 1){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+1.0, posY+random.nextDouble(), posZ+random.nextDouble(), 0, 0, 0, 255, 255, 255);
			}
			if (side == 2){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY, posZ+random.nextDouble(), 0, 0, 0, 255, 255, 255);
			}
			if (side == 3){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+1.0, posZ+random.nextDouble(), 0, 0, 0, 255, 255, 255);
			}
			if (side == 4){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+random.nextDouble(), posZ, 0, 0, 0, 255, 255, 255);
			}
			if (side == 5){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+random.nextDouble(), posZ+1.0, 0, 0, 0, 255, 255, 255);
			}
		}
		lifetime --;
		if (lifetime <= 0){
			this.kill();
			this.getEntityWorld().removeEntity(this);
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.kill();
		this.getEntityWorld().removeEntity(this);
		this.lifetime = compound.getInteger("lifetime");
		this.potency = compound.getInteger("potency");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("lifetime", lifetime);
		compound.setInteger("potency", potency);
	}
}

package elucent.rootsclassic.entity.skeleton;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class EntityFrozenKnight extends EntitySkeleton {

  public static final String NAME = "skeleton_frozen";
  public static boolean appliesSlowPotion = true;

  public EntityFrozenKnight(World world) {
    super(world);
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
  }

  @Override
  protected void initEntityAI() {
    //super.initEntityAI();
    this.tasks.addTask(1, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIRestrictSun(this));
    this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(6, new EntityAILookIdle(this));
    //   this.tasks.addTask(3, new EntityAIAvoidEntity<EntityFrozenKnight>(this, EntityFrozenKnight.class, 6.0F, 1.0D, 1.2D));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    //  this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityMob>(this, EntityMob.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntitySpider>(this, EntitySpider.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityZombie>(this, EntityZombie.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityEnderman>(this, EntityEnderman.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntitySkeleton>(this, EntitySkeleton.class, true));
  }

  @Override
  public boolean canBreatheUnderwater() {
    return false;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SKELETON_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource s) {
    return SoundEvents.ENTITY_SKELETON_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_SKELETON_DEATH;
  }

  @Override
  public IEntityLivingData onInitialSpawn(DifficultyInstance di, IEntityLivingData livingData) {
    //From base entity living class
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextGaussian() * 0.05D, 1));
    float f = di.getClampedAdditionalDifficulty();
    this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
    setCanPickUpLoot(rand.nextFloat() < 0.55F * f);
    return livingData;
  }

  @Override
  public boolean attackEntityAsMob(Entity entityIn) {
    if (appliesSlowPotion
        && entityIn instanceof EntityPlayer
        && entityIn instanceof FakePlayer == false) {
      //is fake player test valid? maybe, im not sure.
      //try catch for double safety \0/
      try {
        EntityPlayer p = (EntityPlayer) entityIn;
        if (p.isPotionActive(MobEffects.SLOWNESS) == false) {
          p.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0)); // is 10seconds
        }
      }
      catch (Exception e) {
        //        Roots.log("Error applying slowness to player: possible ticking, dead, or fake player " + e.getMessage());
      }
    }
    return super.attackEntityAsMob(entityIn);
  }
}

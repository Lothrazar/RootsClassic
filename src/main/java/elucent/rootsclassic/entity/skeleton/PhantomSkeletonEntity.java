package elucent.rootsclassic.entity.skeleton;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RestrictSunGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import elucent.rootsclassic.registry.RootsEntities;

import java.util.function.Predicate;

public class PhantomSkeletonEntity extends SkeletonEntity {

  public static boolean appliesSlowPotion = true;
  private static final Predicate<LivingEntity> SKELETON_SELECTOR = (livingEntity) -> {
    return !(livingEntity instanceof PhantomSkeletonEntity);
  };

  public PhantomSkeletonEntity(EntityType<? extends PhantomSkeletonEntity> type, World worldIn) {
    super(type, worldIn);
  }

  public PhantomSkeletonEntity(World worldIn) {
    super(RootsEntities.PHANTOM_SKELETON.get(), worldIn);
  }

  public static AttributeModifierMap.MutableAttribute registerAttributes() {
    return AbstractSkeletonEntity.createAttributes();
  }

  @Override
  protected void registerGoals() {
    //super.registerGoals();
    this.goalSelector.addGoal(1, new SwimGoal(this));
    this.goalSelector.addGoal(2, new RestrictSunGoal(this));
    this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
    this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
    //   this.goalSelector.addGoal(3, new EntityAIAvoidEntity<PhantomSkeletonEntity>(this, PhantomSkeletonEntity.class, 6.0F, 1.0D, 1.2D));
    this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    //  this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MobEntity.class, true));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SpiderEntity.class, true));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EndermanEntity.class, true));
    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, 10, true, false, SKELETON_SELECTOR));
  }

  @Override
  public boolean canBreatheUnderwater() {
    return false;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.SKELETON_AMBIENT;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource s) {
    return SoundEvents.SKELETON_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.SKELETON_DEATH;
  }

  @Nullable
  @Override
  public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
    getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", random.nextGaussian() * 0.05D, Operation.MULTIPLY_BASE));
    float f = difficultyIn.getSpecialMultiplier();
    this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
    setCanPickUpLoot(random.nextFloat() < 0.55F * f);
    return spawnDataIn;
  }

  @Override
  public boolean doHurtTarget(Entity entityIn) {
    if (appliesSlowPotion && entityIn instanceof PlayerEntity && !(entityIn instanceof FakePlayer)) {
      PlayerEntity p = (PlayerEntity) entityIn;
      if (!p.hasEffect(Effects.MOVEMENT_SLOWDOWN)) {
        p.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10 * 20, 0)); // is 10seconds
      }
    }
    return super.doHurtTarget(entityIn);
  }
}

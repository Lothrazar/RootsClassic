package elucent.rootsclassic.entity.skeleton;

import elucent.rootsclassic.registry.RootsEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
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

import javax.annotation.Nullable;

public class PhantomSkeletonEntity extends SkeletonEntity {
	public static boolean appliesSlowPotion = true;

	public PhantomSkeletonEntity(EntityType<? extends PhantomSkeletonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public PhantomSkeletonEntity(World worldIn) {
		super(RootsEntities.PHANTOM_SKELETON.get(), worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return AbstractSkeletonEntity.registerAttributes();
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, true));
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

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		getAttribute(Attributes.FOLLOW_RANGE).applyPersistentModifier(new AttributeModifier("Random spawn bonus", rand.nextGaussian() * 0.05D, Operation.MULTIPLY_BASE));
		float f = difficultyIn.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);
		return spawnDataIn;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (appliesSlowPotion && entityIn instanceof PlayerEntity && !(entityIn instanceof FakePlayer)) {
			PlayerEntity p = (PlayerEntity) entityIn;
			if (!p.isPotionActive(Effects.SLOWNESS)) {
				p.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10 * 20, 0)); // is 10seconds
			}
		}
		return super.attackEntityAsMob(entityIn);
	}
}

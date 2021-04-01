package teamdraco.bellybutton.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.bellybutton.init.BellyButtonEffects;
import teamdraco.bellybutton.init.BellyButtonItems;

import javax.annotation.Nullable;
import java.util.Objects;

public class EvilDustBunnyEntity extends MonsterEntity {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(EvilDustBunnyEntity.class, DataSerializers.VARINT);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;

    public EvilDustBunnyEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.jumpController = new JumpHelperController(this);
        this.moveController = new EvilDustBunnyEntity.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SIZE, 1);
    }

    protected void setSize(int size, boolean resetHealth) {
        this.dataManager.set(SIZE, size);
        this.recenterBoundingBox();
        this.recalculateSize();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(size * size);
        Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.2F + 0.1F * (float) size);
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(size);
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = size;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
            ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(BellyButtonEffects.ITCHY.get(), 600));
        }

        return flag;
    }

    public int getSize() {
        return this.dataManager.get(SIZE);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Size", this.getSize() - 1);
        compound.putBoolean("wasOnGround", this.wasOnGround);
    }

    public void readAdditional(CompoundNBT compound) {
        int i = compound.getInt("Size");
        if (i < 0) {
            i = 0;
        }

        this.setSize(i + 1, false);
        super.readAdditional(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }

    public boolean isSmall() {
        return this.getSize() <= 1;
    }

    public void tick() {
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int i = this.getSize();

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1.0F;
        }

        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    public void recalculateSize() {
        double d0 = this.getPosX();
        double d1 = this.getPosY();
        double d2 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d0, d1, d2);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (SIZE.equals(key)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
    }

    public EntityType<? extends EvilDustBunnyEntity> getType() {
        return (EntityType<? extends EvilDustBunnyEntity>) super.getType();
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.BLOCK_WOOL_PLACE;
    }

    @Override
    public void remove(boolean keepData) {
        int i = this.getSize();
        if (!this.world.isRemote && i > 1 && this.getShouldBeDead() && !this.removed) {
            ITextComponent itextcomponent = this.getCustomName();
            boolean flag = this.isAIDisabled();
            float f = (float) i / 4.0F;
            int j = i / 2;
            int k = 2;

            for (int l = 0; l < k; ++l) {
                float f1 = ((float) (l % 2) - 0.5F) * f;
                float f2 = ((float) (l / 2) - 0.5F) * f;
                EvilDustBunnyEntity bunny = this.getType().create(this.world);
                if (this.isNoDespawnRequired()) {
                    assert bunny != null;
                    bunny.enablePersistence();
                }

                assert bunny != null;
                bunny.setCustomName(itextcomponent);
                bunny.setNoAI(flag);
                bunny.setInvulnerable(this.isInvulnerable());
                bunny.setSize(j, true);
                bunny.setLocationAndAngles(this.getPosX() + (double) f1, this.getPosY() + 0.5D, this.getPosZ() + (double) f2, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(bunny);
            }
        }

        super.remove(keepData);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i = this.rand.nextInt(3);
        if (dataTag == null) {
            if (i < 2 && this.rand.nextFloat() < 0.5F * difficultyIn.getClampedAdditionalDifficulty()) {
                ++i;
            }

            int j = 1 << i;
            this.setSize(j, true);
        } else {
            if (dataTag.contains("Size", 3)) {
                this.setSize(dataTag.getInt("Size"), false);
            }
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(0.65F * (float) this.getSize());
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    public static AttributeModifierMap.MutableAttribute func_234176_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.getPosY() + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && !path.isFinished()) {
                Vector3d vector3d = path.getPosition(this);
                if (vector3d.y > this.getPosY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D) {
            double d1 = horizontalMag(this.getMotion());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vector3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 1);
        }
    }


    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                    this.moveController.setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            EvilDustBunnyEntity.JumpHelperController jumphelpercontroller = (EvilDustBunnyEntity.JumpHelperController) this.jumpController;
            if (!jumphelpercontroller.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    this.startJumping();
                }
            } else if (!jumphelpercontroller.canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround;
    }

    private void enableJumpControl() {
        ((EvilDustBunnyEntity.JumpHelperController) this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((EvilDustBunnyEntity.JumpHelperController) this.jumpController).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveController.getSpeed() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    public void livingTick() {
        super.livingTick();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }

    }

    public static class JumpHelperController extends JumpController {
        private final EvilDustBunnyEntity bunny;
        private boolean canJump;

        public JumpHelperController(EvilDustBunnyEntity bunny) {
            super(bunny);
            this.bunny = bunny;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        public void tick() {
            if (this.isJumping) {
                this.bunny.startJumping();
                this.isJumping = false;
            }

        }
    }

    static class MoveHelperController extends MovementController {
        private final EvilDustBunnyEntity bunny;
        private double nextJumpSpeed;

        public MoveHelperController(EvilDustBunnyEntity bunny) {
            super(bunny);
            this.bunny = bunny;
        }

        public void tick() {
            if (this.bunny.onGround && !this.bunny.isJumping && !((EvilDustBunnyEntity.JumpHelperController) this.bunny.jumpController).getIsJumping()) {
                this.bunny.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.bunny.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.bunny.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(BellyButtonItems.EVIL_DUSTY_BUNNY_SPAWN_EGG.get());
    }
}
package mod.teamdraco.bellybutton.entity;

import mod.teamdraco.bellybutton.init.BellyButtonItems;
import mod.teamdraco.bellybutton.init.BellyButtonSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class DustBunnyEntity extends CreatureEntity {
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(DustBunnyEntity.class, DataSerializers.VARINT);
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;

    public DustBunnyEntity(EntityType<? extends DustBunnyEntity> type, World worldIn) {
        super(type, worldIn);
        this.jumpController = new DustBunnyEntity.JumpHelperController(this);
        this.moveController = new DustBunnyEntity.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.fromItems(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
        this.goalSelector.addGoal(4, new DustBunnyEntity.AvoidEntityGoal<>(this, OcelotEntity.class, 4.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SIZE, 1);
    }

    protected void setSize(int size, boolean resetHealth) {
        this.dataManager.set(SIZE, size);
        this.recenterBoundingBox();
        this.recalculateSize();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(size * size));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)size));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)size);
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = size;
    }

    public static boolean canBunnySpawn(EntityType<? extends DustBunnyEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.up()).isAir();
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

    public EntityType<? extends DustBunnyEntity> getType() {
        return (EntityType<? extends DustBunnyEntity>)super.getType();
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
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2;

            for(int l = 0; l < k; ++l) {
                float f1 = ((float)(l % 2) - 0.5F) * f;
                float f2 = ((float)(l / 2) - 0.5F) * f;
                DustBunnyEntity bunny = this.getType().create(this.world);
                if (this.isNoDespawnRequired()) {
                    bunny.enablePersistence();
                }

                bunny.setCustomName(itextcomponent);
                bunny.setNoAI(flag);
                bunny.setInvulnerable(this.isInvulnerable());
                bunny.setSize(j, true);
                bunny.setLocationAndAngles(this.getPosX() + (double)f1, this.getPosY() + 0.5D, this.getPosZ() + (double)f2, this.rand.nextFloat() * 360.0F, 0.0F);
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
        }
        else {
            if (dataTag.contains("Size", 3)) {
                this.setSize(dataTag.getInt("Size"), false);
            }
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(0.65F * (float)this.getSize());
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    public static AttributeModifierMap.MutableAttribute func_234176_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 6.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == BellyButtonItems.VACUUM.get()) {
            player.playSound(BellyButtonSounds.VACUUM.get(), 0.4F, 1.0F);
            itemstack.damageItem(1, player, (p_220009_1_) -> {
                p_220009_1_.sendBreakAnimation(player.getActiveHand());
            });
            ItemStack itemstack1 = new ItemStack(BellyButtonItems.DUST_BUNNY.get());
            this.setItemData(itemstack1);
            player.addItemStackToInventory(itemstack1);
            if (!player.inventory.addItemStackToInventory(itemstack1)) {
                player.dropItem(itemstack1, false);
            }

            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(player, hand);
        }
    }

    static class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final DustBunnyEntity rabbit;

        public AvoidEntityGoal(DustBunnyEntity rabbit, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
            super(rabbit, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
            this.rabbit = rabbit;
        }
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
            this.world.setEntityState(this, (byte)1);
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

            DustBunnyEntity.JumpHelperController jumphelpercontroller = (DustBunnyEntity.JumpHelperController)this.jumpController;
            if (!jumphelpercontroller.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vector3d vector3d = new Vector3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && !path.isFinished()) {
                        vector3d = path.getPosition(this);
                    }

                    this.startJumping();
                }
            } else if (!jumphelpercontroller.canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround;
    }

    private void enableJumpControl() {
        ((DustBunnyEntity.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((DustBunnyEntity.JumpHelperController)this.jumpController).setCanJump(false);
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

    public class JumpHelperController extends JumpController {
        private final DustBunnyEntity rabbit;
        private boolean canJump;

        public JumpHelperController(DustBunnyEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
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

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        public void tick() {
            if (this.isJumping) {
                this.rabbit.startJumping();
                this.isJumping = false;
            }

        }
    }

    static class MoveHelperController extends MovementController {
        private final DustBunnyEntity rabbit;
        private double nextJumpSpeed;

        public MoveHelperController(DustBunnyEntity rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public void tick() {
            if (this.rabbit.onGround && !this.rabbit.isJumping && !((DustBunnyEntity.JumpHelperController)this.rabbit.jumpController).getIsJumping()) {
                this.rabbit.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.rabbit.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    protected void setItemData(ItemStack bucket) {
        CompoundNBT compoundnbt = bucket.getOrCreateTag();
        compoundnbt.putInt("Size", this.getSize());
        if (this.hasCustomName()) {
            bucket.setDisplayName(this.getCustomName());
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(BellyButtonItems.DUSTY_BUNNY_SPAWN_EGG.get());
    }
}

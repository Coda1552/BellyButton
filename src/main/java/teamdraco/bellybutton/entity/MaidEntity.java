package teamdraco.bellybutton.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.bellybutton.init.BellyButtonItems;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class MaidEntity extends AbstractIllagerEntity {
    private final Inventory inventory = new Inventory(6);
    private static final Predicate<ItemEntity> TRUSTED_TARGET_SELECTOR = (p_213489_0_) -> {
        return !p_213489_0_.cannotPickup() && p_213489_0_.isAlive();
    };

    public MaidEntity(EntityType<? extends MaidEntity> type, World worldIn) {
        super(type, worldIn);
        this.setCanPickUpLoot(true);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new MaidEntity.FindItemsGoal());
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(6, new LookAtGoal(this, MobEntity.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
        return ArmPose.NEUTRAL;
    }

    public boolean canPickUpItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstackIn);
        if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlotType.OFFHAND && super.canPickUpItem(itemstackIn);
        }
    }

    public boolean canEquipItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        return itemstack.isEmpty();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    public static boolean canMaidSpawn(EntityType<? extends MaidEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getLightValue(pos) < 10 && randomIn.nextFloat() < 0.2;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        ListNBT listnbt = new ListNBT();

        for(int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.write(new CompoundNBT()));
            }
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
        return spawnDataIn;
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        ListNBT listnbt = compound.getList("Inventory", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.inventory.addItem(itemstack);
            }
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(BellyButtonItems.MAID_SPAWN_EGG.get());
    }

    @Override
    public void applyWaveBonus(int wave, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_PILLAGER_CELEBRATE;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    protected void spawnDrops(DamageSource damageSourceIn) {
        ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        if (!itemstack.isEmpty()) {
            this.entityDropItem(itemstack);
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        }
        super.spawnDrops(damageSourceIn);
    }

    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (this.canEquipItem(itemstack)) {

            this.triggerItemPickupTrigger(itemEntity);
            this.setItemStackToSlot(EquipmentSlotType.OFFHAND, itemstack.split(itemstack.getCount()));
            this.inventoryHandsDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 2.0F;
            this.onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.remove();
        }

    }

    class FindItemsGoal extends Goal {
        public FindItemsGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean shouldExecute() {
            if (!MaidEntity.this.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty()) {
                return false;
            }
            else if (MaidEntity.this.getAttackTarget() == null && MaidEntity.this.getRevengeTarget() == null) {
                if (MaidEntity.this.getRNG().nextInt(10) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = MaidEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, MaidEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
                    return !list.isEmpty() && MaidEntity.this.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = MaidEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, MaidEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
            ItemStack itemstack = MaidEntity.this.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                MaidEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2F);
            }

        }

        public void startExecuting() {
            List<ItemEntity> list = MaidEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, MaidEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
            if (!list.isEmpty()) {
                MaidEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), 1.2F);
            }

        }
    }
}

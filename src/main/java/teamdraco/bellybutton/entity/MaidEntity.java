package teamdraco.bellybutton.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.bellybutton.init.BellyButtonEntities;
import teamdraco.bellybutton.init.BellyButtonItems;
import teamdraco.bellybutton.init.BellyButtonSounds;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.entity.monster.AbstractIllagerEntity.ArmPose;

public class MaidEntity extends SpellcastingIllagerEntity {
    private final Inventory inventory = new Inventory(6);
    private static final Predicate<ItemEntity> TRUSTED_TARGET_SELECTOR = (p_213489_0_) -> {
        return !p_213489_0_.hasPickUpDelay() && p_213489_0_.isAlive();
    };

    public MaidEntity(EntityType<? extends MaidEntity> type, World worldIn) {
        super(type, worldIn);
        this.setCanPickUpLoot(true);
        this.xpReward = 10;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MaidEntity.CastingSpellGoal());
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6D, 1.0D));
        this.goalSelector.addGoal(3, new MaidEntity.SummonSpellGoal());
        this.goalSelector.addGoal(4, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new MaidEntity.FindItemsGoal());
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, MobEntity.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return AbstractIllagerEntity.ArmPose.SPELLCASTING;
        } else {
            return this.isCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : ArmPose.NEUTRAL;
        }
    }

    public boolean canTakeItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstackIn);
        if (!this.getItemBySlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlotType.OFFHAND && super.canTakeItem(itemstackIn);
        }
    }

    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.OFFHAND);
        return itemstack.isEmpty();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    public static boolean canMaidSpawn(EntityType<? extends MaidEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getLightEmission(pos) < 10 && randomIn.nextFloat() < 0.2;
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        ListNBT listnbt = new ListNBT();

        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.save(new CompoundNBT()));
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
        return spawnDataIn;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        ListNBT listnbt = compound.getList("Inventory", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            ItemStack itemstack = ItemStack.of(listnbt.getCompound(i));
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
    public void applyRaidBuffs(int wave, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_HURT;
    }

    public boolean isAlliedTo(Entity entityIn) {
        if (super.isAlliedTo(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getMobType() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    protected void dropAllDeathLoot(DamageSource damageSourceIn) {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.OFFHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
        }
        super.dropAllDeathLoot(damageSourceIn);
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (this.canHoldItem(itemstack)) {

            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlotType.OFFHAND, itemstack.split(itemstack.getCount()));
            this.handDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 2.0F;
            this.take(itemEntity, itemstack.getCount());
            itemEntity.remove();
        }
    }

    class CastingSpellGoal extends SpellcastingIllagerEntity.CastingASpellGoal {
        private CastingSpellGoal() {
        }

        public void tick() {
            if (MaidEntity.this.getTarget() != null) {
                MaidEntity.this.getLookControl().setLookAt(MaidEntity.this.getTarget(), (float) MaidEntity.this.getMaxHeadYRot(), (float) MaidEntity.this.getMaxHeadXRot());
            }
        }
    }

    class FindItemsGoal extends Goal {
        public FindItemsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (!MaidEntity.this.getItemBySlot(EquipmentSlotType.OFFHAND).isEmpty()) {
                return false;
            } else if (MaidEntity.this.getTarget() == null && MaidEntity.this.getLastHurtByMob() == null) {
                if (MaidEntity.this.getRandom().nextInt(10) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = MaidEntity.this.level.getEntitiesOfClass(ItemEntity.class, MaidEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
                    return !list.isEmpty() && MaidEntity.this.getItemBySlot(EquipmentSlotType.OFFHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = MaidEntity.this.level.getEntitiesOfClass(ItemEntity.class, MaidEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
            ItemStack itemstack = MaidEntity.this.getItemBySlot(EquipmentSlotType.OFFHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                MaidEntity.this.getNavigation().moveTo(list.get(0), 1.2F);
            }
        }

        public void start() {
            List<ItemEntity> list = MaidEntity.this.level.getEntitiesOfClass(ItemEntity.class, MaidEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
            if (!list.isEmpty()) {
                MaidEntity.this.getNavigation().moveTo(list.get(0), 1.2F);
            }
        }
    }

    class SummonSpellGoal extends SpellcastingIllagerEntity.UseSpellGoal {
        private final EntityPredicate vexCountTargeting = (new EntityPredicate()).range(16.0D).allowUnseeable().ignoreInvisibilityTesting().allowInvulnerable().allowSameTeam();

        private SummonSpellGoal() {
        }

        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                int i = MaidEntity.this.level.getNearbyEntities(VexEntity.class, this.vexCountTargeting, MaidEntity.this, MaidEntity.this.getBoundingBox().inflate(16.0D)).size();
                return MaidEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return 340;
        }

        protected void performSpellCasting() {
            ServerWorld serverworld = (ServerWorld) MaidEntity.this.level;

            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos = MaidEntity.this.blockPosition().offset(-2 + MaidEntity.this.random.nextInt(5), 1, -2 + MaidEntity.this.random.nextInt(5));
                EvilDustBunnyEntity vexentity = BellyButtonEntities.EVIL_DUST_BUNNY.get().create(MaidEntity.this.level);
                vexentity.moveTo(blockpos, 0.0F, 0.0F);
                vexentity.finalizeSpawn(serverworld, MaidEntity.this.level.getCurrentDifficultyAt(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                serverworld.addFreshEntityWithPassengers(vexentity);
            }

        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        protected SpellcastingIllagerEntity.SpellType getSpell() {
            return SpellcastingIllagerEntity.SpellType.SUMMON_VEX;
        }
    }
}

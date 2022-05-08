package teamdraco.bellybutton.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
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

public class MaidEntity extends SpellcasterIllager {
    private final SimpleContainer inventory = new SimpleContainer(6);
    private static final Predicate<ItemEntity> TRUSTED_TARGET_SELECTOR = (p_213489_0_) -> !p_213489_0_.hasPickUpDelay() && p_213489_0_.isAlive();

    public MaidEntity(EntityType<? extends MaidEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setCanPickUpLoot(true);
        this.xpReward = 10;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MaidEntity.CastingSpellGoal());
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
        this.goalSelector.addGoal(3, new MaidEntity.SummonSpellGoal());
        this.goalSelector.addGoal(4, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new MaidEntity.FindItemsGoal());
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        } else {
            return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.NEUTRAL;
        }
    }

    public boolean canTakeItem(ItemStack itemstackIn) {
        EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstackIn);
        if (!this.getItemBySlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlot.OFFHAND && super.canTakeItem(itemstackIn);
        }
    }

    public boolean canHoldItem(ItemStack stack) {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.OFFHAND);
        return itemstack.isEmpty();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    public static boolean canMaidSpawn(EntityType<? extends MaidEntity> type, BlockGetter worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return worldIn.getLightEmission(pos) < 10 && randomIn.nextFloat() < 0.2;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        ListTag list = new ListTag();

        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                list.add(itemstack.save(new CompoundTag()));
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
        return spawnDataIn;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BellyButtonItems.VACUUM.get()));
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        ListTag list = compound.getList("Inventory", 10);

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = ItemStack.of(list.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.inventory.addItem(itemstack);
            }
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(BellyButtonItems.MAID_SPAWN_EGG.get());
    }

    @Override
    public void applyRaidBuffs(int wave, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
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
        } else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    protected void dropAllDeathLoot(DamageSource damageSourceIn) {
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.OFFHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
        super.dropAllDeathLoot(damageSourceIn);
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();

        if (this.canHoldItem(itemstack)) {
            this.playSound(BellyButtonSounds.VACUUM.get(), 1.0F, 1.0F);
            this.onItemPickup(itemEntity);
            this.setItemSlot(EquipmentSlot.OFFHAND, itemstack.split(itemstack.getCount()));
            this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 2.0F;
            this.take(itemEntity, itemstack.getCount());
            itemEntity.discard();
        }
    }

    class CastingSpellGoal extends SpellcasterIllager.SpellcasterCastingSpellGoal {
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
            if (!MaidEntity.this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {
                return false;
            } else if (MaidEntity.this.getTarget() == null && MaidEntity.this.getLastHurtByMob() == null) {
                if (MaidEntity.this.getRandom().nextInt(10) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = MaidEntity.this.level.getEntitiesOfClass(ItemEntity.class, MaidEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
                    return !list.isEmpty() && MaidEntity.this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = MaidEntity.this.level.getEntitiesOfClass(ItemEntity.class, MaidEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), MaidEntity.TRUSTED_TARGET_SELECTOR);
            ItemStack itemstack = MaidEntity.this.getItemBySlot(EquipmentSlot.OFFHAND);
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

    class SummonSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        private SummonSpellGoal() {
        }

        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                int i = MaidEntity.this.level.getNearbyEntities(EvilDustBunnyEntity.class, TargetingConditions.forCombat(), MaidEntity.this, MaidEntity.this.getBoundingBox().inflate(16.0D)).size();
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
            ServerLevel serverworld = (ServerLevel) MaidEntity.this.level;

            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos = MaidEntity.this.blockPosition().offset(-2 + MaidEntity.this.random.nextInt(5), 1, -2 + MaidEntity.this.random.nextInt(5));
                EvilDustBunnyEntity bunny = BellyButtonEntities.EVIL_DUST_BUNNY.get().create(MaidEntity.this.level);
                bunny.moveTo(blockpos, 0.0F, 0.0F);
                bunny.finalizeSpawn(serverworld, MaidEntity.this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
                serverworld.addFreshEntityWithPassengers(bunny);
            }

        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        protected SpellcasterIllager.IllagerSpell getSpell() {
            return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
        }
    }
}

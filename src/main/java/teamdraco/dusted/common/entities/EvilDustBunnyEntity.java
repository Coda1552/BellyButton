package teamdraco.dusted.common.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import teamdraco.dusted.init.BellyButtonItems;

public class EvilDustBunnyEntity extends DustBunnyEntity {

    public EvilDustBunnyEntity(EntityType<? extends DustBunnyEntity> type, Level worldIn) {
        super(type, worldIn);
        this.jumpControl = new DustBunnyEntity.JumpHelperController(this);
        this.moveControl = new DustBunnyEntity.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(BellyButtonItems.EVIL_DUSTY_BUNNY_SPAWN_EGG.get());
    }
}
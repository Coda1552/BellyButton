package teamdraco.bellybutton.items;

import teamdraco.bellybutton.init.BellyButtonEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

import net.minecraft.item.Item.Properties;

public class DustBunnyItem extends Item {
    public DustBunnyItem(java.util.function.Supplier<? extends EntityType<?>> bunny, Properties properties) {
        super(properties);
    }

    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }
            EntityType<?> entitytype = BellyButtonEntities.DUST_BUNNY.get();
            if (entitytype.spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos1, SpawnReason.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                if(!context.getPlayer().abilities.instabuild) {
                    itemstack.shrink(1);
                }
            }
            return ActionResultType.SUCCESS;
        }
    }
}

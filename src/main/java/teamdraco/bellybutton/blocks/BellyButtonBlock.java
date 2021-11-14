package teamdraco.bellybutton.blocks;

import teamdraco.bellybutton.init.BellyButtonItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BellyButtonBlock extends AbstractButtonBlock {
    public BellyButtonBlock(AbstractBlock.Properties properties) {
        super(false, properties);
    }

    protected SoundEvent getSound(boolean p_196369_1_) {
        return p_196369_1_ ? SoundEvents.SLIME_BLOCK_STEP : SoundEvents.SLIME_BLOCK_BREAK;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isClientSide) {
            player.playNotifySound(SoundEvents.WOOL_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
            player.swing(handIn, true);
            if (this.RANDOM.nextInt(50) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, (double) (pos.getY() + 0.5D), (double) pos.getZ() + 0.5D, new ItemStack(BellyButtonItems.LINT.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
            if (this.RANDOM.nextInt(250) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, (double) (pos.getY() + 0.5D), (double) pos.getZ() + 0.5D, new ItemStack(BellyButtonItems.MUSIC_DISC_BELLY_BOPPIN.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
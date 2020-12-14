package mod.teamdraco.bellybutton.blocks;

import mod.teamdraco.bellybutton.init.BellyButtonItems;
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

    protected SoundEvent getSoundEvent(boolean p_196369_1_) {
        return p_196369_1_ ? SoundEvents.BLOCK_SLIME_BLOCK_STEP : SoundEvents.BLOCK_SLIME_BLOCK_BREAK;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            player.playSound(SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
            if(!player.isCreative()) heldItem.shrink(1);
            player.swing(handIn, true);
            if(this.RANDOM.nextInt(50) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getEntityWorld(), (double) pos.getX() + 0.5D, (double) (pos.getY() + 0.5D), (double) pos.getZ() + 0.5D, new ItemStack(BellyButtonItems.LINT.get(), 1));
                player.getEntityWorld().addEntity(itemEntity);
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
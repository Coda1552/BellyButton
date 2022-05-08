package teamdraco.bellybutton.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import teamdraco.bellybutton.init.BellyButtonItems;

public class BellyButtonBlock extends ButtonBlock {

    public BellyButtonBlock(BlockBehaviour.Properties properties) {
        super(false, properties);
    }

    protected SoundEvent getSound(boolean p_196369_1_) {
        return p_196369_1_ ? SoundEvents.SLIME_BLOCK_STEP : SoundEvents.SLIME_BLOCK_BREAK;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide) {
            player.playNotifySound(SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 0.8f, 1.0f);
            player.swing(handIn, true);
            if (this.RANDOM.nextInt(50) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, new ItemStack(BellyButtonItems.LINT.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
            if (this.RANDOM.nextInt(5000) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, new ItemStack(BellyButtonItems.MUSIC_DISC_BELLY_BOPPIN.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
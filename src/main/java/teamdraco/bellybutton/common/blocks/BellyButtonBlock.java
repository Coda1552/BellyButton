package teamdraco.bellybutton.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import teamdraco.bellybutton.common.dimension.NavelTeleporter;
import teamdraco.bellybutton.init.BBBlocks;
import teamdraco.bellybutton.init.BBDimension;
import teamdraco.bellybutton.init.BBItems;

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
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, new ItemStack(BBItems.LINT.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
            if (this.RANDOM.nextInt(5000) == 0) {
                ItemEntity itemEntity = new ItemEntity(player.getCommandSenderWorld(), (double) pos.getX() + 0.5D, pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, new ItemStack(BBItems.MUSIC_DISC_BELLY_BOPPIN.get(), 1));
                player.getCommandSenderWorld().addFreshEntity(itemEntity);
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (!level.isClientSide() && entity instanceof ThrownEnderpearl pearl && pearl.getOwner() instanceof Player thrower) {
            ResourceKey<Level> resourcekey = BBDimension.THE_NAVEL;
            ServerLevel serverlevel = ((ServerLevel) level).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return;
            }
            NavelTeleporter tp = new NavelTeleporter(serverlevel);

            tp.placeEntity(entity, (ServerLevel) level, serverlevel, entity.getYRot(), a -> {
                makeHole(serverlevel, pos);
                return entity;
            });
            thrower.changeDimension(serverlevel);
            thrower.changeDimension(serverlevel, tp);
        }
    }

    public static void makeHole(ServerLevel level, BlockPos pos) {
        int i = pos.getX();
        int j = pos.getY() - 1;
        int k = pos.getZ();
        BlockPos.betweenClosed(i - 4, j + 1, k - 4, i + 4, j + 3, k + 4).forEach((p_207578_) -> level.setBlockAndUpdate(p_207578_, Blocks.AIR.defaultBlockState()));
        //level.setBlock(pos, BBBlocks.BELLY_BUTTON.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP).setValue(FACE, AttachFace.FLOOR), 2001);
    }

}
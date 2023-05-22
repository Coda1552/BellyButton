package teamdraco.bellybutton.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.common.blocks.BellyButtonBlock;
import teamdraco.bellybutton.common.blocks.LintCarpetBlock;

public class BBBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BellyButton.MOD_ID);

    public static final RegistryObject<Block> BELLY_BUTTON = BLOCKS.register("belly_button", () -> new BellyButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> LINT_BLOCK = BLOCKS.register("lint_block", () -> new Block(BlockBehaviour.Properties.of(Material.WOOL).strength(0.5f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LINT_CARPET = BLOCKS.register("lint_carpet", () -> new LintCarpetBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.WOOL)));
}
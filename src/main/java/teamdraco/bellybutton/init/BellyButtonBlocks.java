package teamdraco.bellybutton.init;

import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.blocks.BellyButtonBlock;
import teamdraco.bellybutton.blocks.LintCarpetBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.block.AbstractBlock;

public class BellyButtonBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, BellyButton.MOD_ID);

    public static final RegistryObject<Block> BELLY_BUTTON = REGISTER.register("belly_button", () -> new BellyButtonBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> LINT_BLOCK = REGISTER.register("lint_block", () -> new Block(AbstractBlock.Properties.of(Material.WOOL).strength(0.5f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> LINT_CARPET = REGISTER.register("lint_carpet", () -> new LintCarpetBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(0.5f).sound(SoundType.WOOL)));
}
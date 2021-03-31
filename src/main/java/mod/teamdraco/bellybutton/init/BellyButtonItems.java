package mod.teamdraco.bellybutton.init;

import mod.teamdraco.bellybutton.BellyButton;
import mod.teamdraco.bellybutton.items.BellyButtonSpawnEggItem;
import mod.teamdraco.bellybutton.items.DustBunnyItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BellyButton.MOD_ID);

    public static final RegistryObject<Item> LINT = REGISTER.register("lint", () -> new Item(new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> VACUUM = REGISTER.register("vacuum", () -> new Item(new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DUST_BUNNY = REGISTER.register("dust_bunny", () -> new DustBunnyItem(() -> BellyButtonEntities.DUST_BUNNY.get(), new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));

    public static final RegistryObject<BlockItem> BELLY_BUTTON = REGISTER.register("belly_button", () -> new BlockItem(BellyButtonBlocks.BELLY_BUTTON.get(), new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<BlockItem> LINT_BLOCK = REGISTER.register("lint_block", () -> new BlockItem(BellyButtonBlocks.LINT_BLOCK.get(), new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<BlockItem> LINT_CARPET = REGISTER.register("lint_carpet", () -> new BlockItem(BellyButtonBlocks.LINT_CARPET.get(), new Item.Properties().group(BellyButton.GROUP)));

    public static final RegistryObject<BellyButtonSpawnEggItem> DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("dust_bunny_spawn_egg", () -> new BellyButtonSpawnEggItem(BellyButtonEntities.DUST_BUNNY, 0x726b65, 0xa9a6a1, new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<BellyButtonSpawnEggItem> MAID_SPAWN_EGG = REGISTER.register("maid_spawn_egg", () -> new BellyButtonSpawnEggItem(BellyButtonEntities.MAID, 0x29242c, 0xd7d7d7, new Item.Properties().group(BellyButton.GROUP)));
}
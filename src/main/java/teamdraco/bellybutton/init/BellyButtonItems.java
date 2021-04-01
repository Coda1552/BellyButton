package teamdraco.bellybutton.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.items.BellyButtonSpawnEggItem;
import teamdraco.bellybutton.items.DustBunnyItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.bellybutton.items.LintSweaterItem;
import teamdraco.bellybutton.items.MaidArmorItem;

public class BellyButtonItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BellyButton.MOD_ID);

    public static final RegistryObject<Item> LINT = REGISTER.register("lint", () -> new Item(new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> VACUUM = REGISTER.register("vacuum", () -> new Item(new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> DUST_BUNNY = REGISTER.register("dust_bunny", () -> new DustBunnyItem(BellyButtonEntities.DUST_BUNNY::get, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> MUSIC_DISC_BELLY_BOPPIN = REGISTER.register("music_disc_belly_boppin", () -> new MusicDiscItem(15, BellyButtonSounds.BELLY_BOPPIN::get, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LINT_SWEATER = REGISTER.register("lint_sweater", () -> new LintSweaterItem(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> MAID_HAIRBAND = REGISTER.register("maid_hairband", () -> new MaidArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> MAID_DRESS = REGISTER.register("maid_dress", () -> new MaidArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> MAID_SKIRT = REGISTER.register("maid_skirt", () -> new MaidArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.LEGS, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> MAID_SHOES = REGISTER.register("maid_shoes", () -> new MaidArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.FEET, new Item.Properties().group(BellyButton.GROUP).maxStackSize(1)));

    public static final RegistryObject<Item> BELLY_BUTTON = REGISTER.register("belly_button", () -> new BlockItem(BellyButtonBlocks.BELLY_BUTTON.get(), new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> LINT_BLOCK = REGISTER.register("lint_block", () -> new BlockItem(BellyButtonBlocks.LINT_BLOCK.get(), new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> LINT_CARPET = REGISTER.register("lint_carpet", () -> new BlockItem(BellyButtonBlocks.LINT_CARPET.get(), new Item.Properties().group(BellyButton.GROUP)));

    public static final RegistryObject<Item> DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("dust_bunny_spawn_egg", () -> new BellyButtonSpawnEggItem(BellyButtonEntities.DUST_BUNNY, 0x726b65, 0xa9a6a1, new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> MAID_SPAWN_EGG = REGISTER.register("maid_spawn_egg", () -> new BellyButtonSpawnEggItem(BellyButtonEntities.MAID, 0x29242c, 0xd7d7d7, new Item.Properties().group(BellyButton.GROUP)));
    public static final RegistryObject<Item> EVIL_DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("evil_dust_bunny_spawn_egg", () -> new BellyButtonSpawnEggItem(BellyButtonEntities.EVIL_DUST_BUNNY, 0x726b65, 0x3d3d3d, new Item.Properties().group(BellyButton.GROUP)));

}
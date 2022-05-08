package teamdraco.bellybutton.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.bellybutton.common.items.DustBunnyItem;
import teamdraco.bellybutton.common.items.ItchySweaterItem;
import teamdraco.bellybutton.common.items.MaidArmorItem;

public class BellyButtonItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, BellyButton.MOD_ID);

    public static final RegistryObject<Item> DUST = REGISTER.register("dust", () -> new Item(new Item.Properties().tab(BellyButton.GROUP)));
    public static final RegistryObject<Item> VACUUM = REGISTER.register("vacuum", () -> new Item(new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DUST_BUNNY = REGISTER.register("dust_bunny", () -> new DustBunnyItem(BellyButtonEntities.DUST_BUNNY, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MUSIC_DISC_BELLY_BOPPIN = REGISTER.register("music_disc_belly_boppin", () -> new RecordItem(15, BellyButtonSounds.BELLY_BOPPIN, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ITCHY_SWEATER = REGISTER.register("itchy_sweater", () -> new ItchySweaterItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_HAIRBAND = REGISTER.register("maid_hairband", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_DRESS = REGISTER.register("maid_dress", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_SKIRT = REGISTER.register("maid_skirt", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_SHOES = REGISTER.register("maid_shoes", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Properties().tab(BellyButton.GROUP).stacksTo(1)));

    public static final RegistryObject<Item> DUST_BLOCK = REGISTER.register("dust_block", () -> new BlockItem(BellyButtonBlocks.DUST_BLOCK.get(), new Item.Properties().tab(BellyButton.GROUP)));
    public static final RegistryObject<Item> DUST_CARPET = REGISTER.register("dust_carpet", () -> new BlockItem(BellyButtonBlocks.DUST_CARPET.get(), new Item.Properties().tab(BellyButton.GROUP)));

    public static final RegistryObject<Item> DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("dust_bunny_spawn_egg", () -> new ForgeSpawnEggItem(BellyButtonEntities.DUST_BUNNY, 0x726b65, 0xa9a6a1, new Item.Properties().tab(BellyButton.GROUP)));
    public static final RegistryObject<Item> MAID_SPAWN_EGG = REGISTER.register("maid_spawn_egg", () -> new ForgeSpawnEggItem(BellyButtonEntities.MAID, 0x29242c, 0xd7d7d7, new Item.Properties().tab(BellyButton.GROUP)));
    public static final RegistryObject<Item> EVIL_DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("evil_dust_bunny_spawn_egg", () -> new ForgeSpawnEggItem(BellyButtonEntities.EVIL_DUST_BUNNY, 0x726b65, 0x3d3d3d, new Item.Properties().tab(BellyButton.GROUP)));

}
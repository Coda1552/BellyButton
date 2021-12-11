package teamdraco.dusted.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.dusted.Dusted;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.dusted.common.items.DustBunnyItem;
import teamdraco.dusted.common.items.ItchySweaterItem;
import teamdraco.dusted.common.items.MaidArmorItem;

public class DustedItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Dusted.MOD_ID);

    public static final RegistryObject<Item> DUST = REGISTER.register("dust", () -> new Item(new Item.Properties().tab(Dusted.GROUP)));
    public static final RegistryObject<Item> VACUUM = REGISTER.register("vacuum", () -> new Item(new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DUST_BUNNY = REGISTER.register("dust_bunny", () -> new DustBunnyItem(DustedEntities.DUST_BUNNY::get, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MUSIC_DISC_BELLY_BOPPIN = REGISTER.register("music_disc_belly_boppin", () -> new RecordItem(15, DustedSounds.BELLY_BOPPIN::get, new Item.Properties().tab(Dusted.GROUP).stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ITCHY_SWEATER = REGISTER.register("itchy_sweater", () -> new ItchySweaterItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_HAIRBAND = REGISTER.register("maid_hairband", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_DRESS = REGISTER.register("maid_dress", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_SKIRT = REGISTER.register("maid_skirt", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> MAID_SHOES = REGISTER.register("maid_shoes", () -> new MaidArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Properties().tab(Dusted.GROUP).stacksTo(1)));

    public static final RegistryObject<Item> DUST_BLOCK = REGISTER.register("dust_block", () -> new BlockItem(DustedBlocks.DUST_BLOCK.get(), new Item.Properties().tab(Dusted.GROUP)));
    public static final RegistryObject<Item> DUST_CARPET = REGISTER.register("dust_carpet", () -> new BlockItem(DustedBlocks.DUST_CARPET.get(), new Item.Properties().tab(Dusted.GROUP)));

    public static final RegistryObject<Item> DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("dust_bunny_spawn_egg", () -> new ForgeSpawnEggItem(DustedEntities.DUST_BUNNY, 0x726b65, 0xa9a6a1, new Item.Properties().tab(Dusted.GROUP)));
    public static final RegistryObject<Item> MAID_SPAWN_EGG = REGISTER.register("maid_spawn_egg", () -> new ForgeSpawnEggItem(DustedEntities.MAID, 0x29242c, 0xd7d7d7, new Item.Properties().tab(Dusted.GROUP)));
    public static final RegistryObject<Item> EVIL_DUSTY_BUNNY_SPAWN_EGG = REGISTER.register("evil_dust_bunny_spawn_egg", () -> new ForgeSpawnEggItem(DustedEntities.EVIL_DUST_BUNNY, 0x726b65, 0x3d3d3d, new Item.Properties().tab(Dusted.GROUP)));

}
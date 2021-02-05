package mod.teamdraco.bellybutton;

import mod.teamdraco.bellybutton.entity.DustBunnyEntity;
import mod.teamdraco.bellybutton.init.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BellyButton.MOD_ID)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BellyButton {
    public static final String MOD_ID = "bellybutton";

    public BellyButton() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::registerCommon);

        BellyButtonEnchantments.REGISTER.register(bus);
        BellyButtonSounds.REGISTER.register(bus);
        BellyButtonItems.REGISTER.register(bus);
        BellyButtonBlocks.REGISTER.register(bus);
        BellyButtonEntities.REGISTER.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(BellyButtonEntities.DUST_BUNNY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DustBunnyEntity::canBunnySpawn);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyEntity.func_234176_m_().create());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.DESERT) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(BellyButtonEntities.DUST_BUNNY.get(), 4, 1, 1));
        }
    }

    public static void registerBiome(Biome biome, BiomeManager.BiomeType type, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("bellybutton:dermis_forest")), types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation("bellybutton:dermis_forest")), 50));
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BellyButtonBlocks.BELLY_BUTTON.get());
        }
    };
}

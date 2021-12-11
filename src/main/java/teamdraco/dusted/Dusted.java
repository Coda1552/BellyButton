package teamdraco.dusted;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import teamdraco.dusted.common.entities.DustBunnyEntity;
import teamdraco.dusted.common.entities.EvilDustBunnyEntity;
import teamdraco.dusted.common.entities.MaidEntity;
import teamdraco.dusted.init.*;

@Mod(Dusted.MOD_ID)
@Mod.EventBusSubscriber(modid = Dusted.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Dusted {
    public static final String MOD_ID = "dusted";

    public Dusted() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);

        DustedEnchantments.REGISTER.register(bus);
        DustedSounds.REGISTER.register(bus);
        DustedEffects.POTIONS.register(bus);
        DustedEffects.EFFECTS.register(bus);
        DustedItems.REGISTER.register(bus);
        DustedBlocks.REGISTER.register(bus);
        DustedEntities.REGISTER.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        SpawnPlacements.register(DustedEntities.DUST_BUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DustBunnyEntity::canBunnySpawn);
        SpawnPlacements.register(DustedEntities.MAID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MaidEntity::canMaidSpawn);
        DustedEffects.brewingRecipes();
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DustedEntities.DUST_BUNNY.get(), DustBunnyEntity.createAttributes().build());
        event.put(DustedEntities.MAID.get(), MaidEntity.createAttributes().build());
        event.put(DustedEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyEntity.createAttributes().build());
    }

    public final static CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DustedItems.DUST_BUNNY.get());
        }
    };
}

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

@Mod(BellyButton.MOD_ID)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BellyButton {
    public static final String MOD_ID = "dusted";

    public BellyButton() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);

        BellyButtonEnchantments.REGISTER.register(bus);
        BellyButtonSounds.REGISTER.register(bus);
        BellyButtonEffects.POTIONS.register(bus);
        BellyButtonEffects.EFFECTS.register(bus);
        BellyButtonItems.REGISTER.register(bus);
        BellyButtonBlocks.REGISTER.register(bus);
        BellyButtonEntities.REGISTER.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        SpawnPlacements.register(BellyButtonEntities.DUST_BUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DustBunnyEntity::canBunnySpawn);
        SpawnPlacements.register(BellyButtonEntities.MAID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MaidEntity::canMaidSpawn);
        BellyButtonEffects.brewingRecipes();
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyEntity.createAttributes().build());
        event.put(BellyButtonEntities.MAID.get(), MaidEntity.createAttributes().build());
        event.put(BellyButtonEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyEntity.createAttributes().build());
    }

    public final static CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BellyButtonItems.DUST_BUNNY.get());
        }
    };
}

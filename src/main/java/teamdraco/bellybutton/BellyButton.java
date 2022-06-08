package teamdraco.bellybutton;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import teamdraco.bellybutton.common.entities.DustBunnyEntity;
import teamdraco.bellybutton.common.entities.EvilDustBunnyEntity;
import teamdraco.bellybutton.common.entities.MaidEntity;
import teamdraco.bellybutton.init.*;

@Mod(BellyButton.MOD_ID)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BellyButton {
    public static final String MOD_ID = "bellybutton";

    public BellyButton() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);

        BBEnchantments.REGISTER.register(bus);
        BBSounds.REGISTER.register(bus);
        BBEffects.POTIONS.register(bus);
        BBEffects.EFFECTS.register(bus);
        BBItems.REGISTER.register(bus);
        BBBlocks.REGISTER.register(bus);
        BBEntities.REGISTER.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        SpawnPlacements.register(BBEntities.DUST_BUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DustBunnyEntity::canBunnySpawn);
        SpawnPlacements.register(BBEntities.MAID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MaidEntity::canMaidSpawn);
        BBEffects.brewingRecipes();

        event.enqueueWork(() -> {
            Raid.RaiderType.create("maid", BBEntities.MAID.get(), new int[] {0, 1, 2, 2, 1, 2, 2, 3 });
        });
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(BBEntities.DUST_BUNNY.get(), DustBunnyEntity.createAttributes().build());
        event.put(BBEntities.MAID.get(), MaidEntity.createAttributes().build());
        event.put(BBEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyEntity.createAttributes().build());
    }

    public final static CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BBItems.DUST_BUNNY.get());
        }
    };
}

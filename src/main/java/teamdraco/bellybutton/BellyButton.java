package teamdraco.bellybutton;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import teamdraco.bellybutton.capabilities.PlayerNavelData;
import teamdraco.bellybutton.capabilities.PlayerNavelProvider;
import teamdraco.bellybutton.common.entities.DustBunnyEntity;
import teamdraco.bellybutton.common.entities.EvilDustBunnyEntity;
import teamdraco.bellybutton.common.entities.MaidEntity;
import teamdraco.bellybutton.registry.*;

@Mod(BellyButton.MOD_ID)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BellyButton {
    public static final String MOD_ID = "bellybutton";

    public BellyButton() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::registerCommon);
        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerCapabilities);

        forgeBus.addGenericListener(Entity.class, this::attachCapabilitiesPlayer);
        forgeBus.addListener(this::onPlayerCloned);

        BBEnchantments.ENCHANTMENTS.register(bus);
        BBSounds.SOUNDS.register(bus);
        BBEffects.POTIONS.register(bus);
        BBEffects.EFFECTS.register(bus);
        BBItems.ITEMS.register(bus);
        BBBlocks.BLOCKS.register(bus);
        BBEntities.ENTITIES.register(bus);
        BBPoiTypes.POIS.register(bus);
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

    private void attachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player) {
            if (!e.getObject().getCapability(PlayerNavelProvider.NAVEL_POS).isPresent()) {
                e.addCapability(new ResourceLocation(BellyButton.MOD_ID, "navelpos"), new PlayerNavelProvider());
            }
        }
    }

    private void onPlayerCloned(PlayerEvent.Clone e) {
        if (e.isWasDeath()) {
            e.getOriginal().getCapability(PlayerNavelProvider.NAVEL_POS).ifPresent(oldStore -> {
                e.getPlayer().getCapability(PlayerNavelProvider.NAVEL_POS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent e) {
        e.register(PlayerNavelData.class);
    }

}

package teamdraco.bellybutton;

import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import teamdraco.bellybutton.entity.DustBunnyEntity;
import teamdraco.bellybutton.entity.EvilDustBunnyEntity;
import teamdraco.bellybutton.entity.MaidEntity;
import teamdraco.bellybutton.init.*;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
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
        BellyButtonEffects.POTIONS.register(bus);
        BellyButtonEffects.EFFECTS.register(bus);
        BellyButtonItems.REGISTER.register(bus);
        BellyButtonBlocks.REGISTER.register(bus);
        BellyButtonEntities.REGISTER.register(bus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        EntitySpawnPlacementRegistry.register(BellyButtonEntities.DUST_BUNNY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DustBunnyEntity::canBunnySpawn);
        EntitySpawnPlacementRegistry.register(BellyButtonEntities.MAID.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaidEntity::canMaidSpawn);
        BellyButtonEffects.brewingRecipes();
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(BellyButtonEntities.MAID.get(), MaidEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(BellyButtonEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyEntity.createAttributes().build());
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BellyButtonBlocks.BELLY_BUTTON.get());
        }
    };
}

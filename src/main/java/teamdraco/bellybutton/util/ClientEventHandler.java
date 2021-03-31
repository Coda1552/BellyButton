package teamdraco.bellybutton.util;

import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.client.render.DustBunnyRenderer;
import teamdraco.bellybutton.client.render.EvilDustBunnyProjectileRenderer;
import teamdraco.bellybutton.client.render.EvilDustBunnyRenderer;
import teamdraco.bellybutton.client.render.MaidRenderer;
import teamdraco.bellybutton.entity.EvilDustBunnyEntity;
import teamdraco.bellybutton.init.BellyButtonBlocks;
import teamdraco.bellybutton.init.BellyButtonEntities;
import teamdraco.bellybutton.items.BellyButtonSpawnEggItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BellyButtonEntities.MAID.get(), MaidRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BellyButtonEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BellyButtonEntities.EVIL_DUST_BUNNY_PROJECTILE.get(), EvilDustBunnyProjectileRenderer::new);

        RenderTypeLookup.setRenderLayer(BellyButtonBlocks.LINT_CARPET.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((BellyButtonSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (BellyButtonSpawnEggItem e : BellyButtonSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}

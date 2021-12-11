package teamdraco.dusted.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamdraco.dusted.BellyButton;
import teamdraco.dusted.client.render.DustBunnyRenderer;
import teamdraco.dusted.client.render.EvilDustBunnyRenderer;
import teamdraco.dusted.client.render.MaidRenderer;
import teamdraco.dusted.init.BellyButtonBlocks;
import teamdraco.dusted.init.BellyButtonEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyRenderer::new);
        EntityRenderers.register(BellyButtonEntities.MAID.get(), MaidRenderer::new);
        EntityRenderers.register(BellyButtonEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(BellyButtonBlocks.LINT_CARPET.get(), RenderType.cutout());
    }
}

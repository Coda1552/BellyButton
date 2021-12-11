package teamdraco.dusted.client;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import teamdraco.dusted.Dusted;
import teamdraco.dusted.client.model.DustBunnyModel;
import teamdraco.dusted.client.render.DustBunnyRenderer;
import teamdraco.dusted.client.render.EvilDustBunnyRenderer;
import teamdraco.dusted.client.render.MaidRenderer;
import teamdraco.dusted.init.DustedBlocks;
import teamdraco.dusted.init.DustedEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Dusted.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(DustedEntities.DUST_BUNNY.get(), DustBunnyRenderer::new);
        EntityRenderers.register(DustedEntities.MAID.get(), MaidRenderer::new);
        EntityRenderers.register(DustedEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyRenderer::new);

        ForgeHooksClient.registerLayerDefinition(MaidRenderer.MAID, IllagerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(EvilDustBunnyRenderer.DUST_BUNNY, DustBunnyModel::createLayerDefinition);
        ForgeHooksClient.registerLayerDefinition(DustBunnyRenderer.DUST_BUNNY, DustBunnyModel::createLayerDefinition);

        ItemBlockRenderTypes.setRenderLayer(DustedBlocks.LINT_CARPET.get(), RenderType.cutout());
    }
}

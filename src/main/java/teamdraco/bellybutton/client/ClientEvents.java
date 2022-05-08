package teamdraco.bellybutton.client;

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
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.client.model.DustBunnyModel;
import teamdraco.bellybutton.client.render.DustBunnyRenderer;
import teamdraco.bellybutton.client.render.EvilDustBunnyRenderer;
import teamdraco.bellybutton.client.render.MaidRenderer;
import teamdraco.bellybutton.init.BellyButtonBlocks;
import teamdraco.bellybutton.init.BellyButtonEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyRenderer::new);
        EntityRenderers.register(BellyButtonEntities.MAID.get(), MaidRenderer::new);
        EntityRenderers.register(BellyButtonEntities.EVIL_DUST_BUNNY.get(), EvilDustBunnyRenderer::new);

        ForgeHooksClient.registerLayerDefinition(MaidRenderer.MAID, IllagerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(EvilDustBunnyRenderer.DUST_BUNNY, DustBunnyModel::createLayerDefinition);
        ForgeHooksClient.registerLayerDefinition(DustBunnyRenderer.DUST_BUNNY, DustBunnyModel::createLayerDefinition);

        ItemBlockRenderTypes.setRenderLayer(BellyButtonBlocks.DUST_CARPET.get(), RenderType.cutout());
    }
}

package mod.teamdraco.bellybutton.util;

import mod.teamdraco.bellybutton.BellyButton;
import mod.teamdraco.bellybutton.client.render.DustBunnyRenderer;
import mod.teamdraco.bellybutton.init.BellyButtonBlocks;
import mod.teamdraco.bellybutton.init.BellyButtonEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(BellyButtonEntities.DUST_BUNNY.get(), DustBunnyRenderer::new);
        RenderTypeLookup.setRenderLayer(BellyButtonBlocks.LINT_CARPET.get(), RenderType.getCutout());
    }
}

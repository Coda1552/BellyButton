package teamdraco.bellybutton.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.client.model.DustBunnyModel;
import teamdraco.bellybutton.common.entities.EvilDustBunnyEntity;

@OnlyIn(Dist.CLIENT)
public class EvilDustBunnyRenderer extends MobRenderer<EvilDustBunnyEntity, DustBunnyModel<EvilDustBunnyEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/evil_dust_bunny.png");
    public static final ModelLayerLocation DUST_BUNNY = new ModelLayerLocation(new ResourceLocation(BellyButton.MOD_ID, "dust_bunny"), "main");

    public EvilDustBunnyRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DustBunnyModel<>(manager.bakeLayer(DUST_BUNNY)), 0.35F);
    }

    protected void scale(EvilDustBunnyEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        float f1 = (float)entity.getSize();
        float f2 = Mth.lerp(partialTickTime, entity.prevSquishFactor, entity.squishFactor) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(EvilDustBunnyEntity entity) {
        return TEXTURE;
    }
}
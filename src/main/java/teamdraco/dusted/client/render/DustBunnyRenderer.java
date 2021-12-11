package teamdraco.dusted.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.dusted.Dusted;
import teamdraco.dusted.client.model.DustBunnyModel;
import teamdraco.dusted.common.entities.DustBunnyEntity;

@OnlyIn(Dist.CLIENT)
public class DustBunnyRenderer extends MobRenderer<DustBunnyEntity, DustBunnyModel<DustBunnyEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Dusted.MOD_ID, "textures/entity/dust_bunny.png");
    private static final ResourceLocation BIG_CHUNGUS = new ResourceLocation(Dusted.MOD_ID, "textures/entity/big_chungus.png");
    public static final ModelLayerLocation DUST_BUNNY = new ModelLayerLocation(new ResourceLocation(Dusted.MOD_ID, "dust_bunny"), "main");

    public DustBunnyRenderer(EntityRendererProvider.Context manager) {
        super(manager, new DustBunnyModel<>(manager.bakeLayer(DUST_BUNNY)), 0.2F);
    }

    protected void scale(DustBunnyEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        float f1 = (float)entity.getSize();
        float f2 = Mth.lerp(partialTickTime, entity.prevSquishFactor, entity.squishFactor) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(DustBunnyEntity entity) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if (s != null && "Big Chungus".equals(s)) {
            return BIG_CHUNGUS;
        }
        else {
            return TEXTURE;
        }
    }
}
package teamdraco.dusted.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.dusted.BellyButton;
import teamdraco.dusted.client.model.DustBunnyModel;
import teamdraco.dusted.common.entities.EvilDustBunnyEntity;

@OnlyIn(Dist.CLIENT)
public class EvilDustBunnyRenderer extends MobRenderer<EvilDustBunnyEntity, DustBunnyModel<EvilDustBunnyEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/evil_dust_bunny.png");

    public EvilDustBunnyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DustBunnyModel<>(), 0.35F);
    }

    protected void scale(EvilDustBunnyEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
        float f1 = (float)entity.getSize();
        float f2 = MathHelper.lerp(partialTickTime, entity.prevSquishFactor, entity.squishFactor) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(EvilDustBunnyEntity entity) {
        return TEXTURE;
    }
}
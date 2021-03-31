package teamdraco.bellybutton.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.client.model.DustBunnyModel;
import teamdraco.bellybutton.entity.DustBunnyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustBunnyRenderer extends MobRenderer<DustBunnyEntity, DustBunnyModel<DustBunnyEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/dust_bunny.png");
    private static final ResourceLocation BIG_CHUNGUS = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/big_chungus.png");

    public DustBunnyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DustBunnyModel<>(), 0.2F);
    }

    protected void preRenderCallback(DustBunnyEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
        float f1 = (float)entity.getSize();
        float f2 = MathHelper.lerp(partialTickTime, entity.prevSquishFactor, entity.squishFactor) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getEntityTexture(DustBunnyEntity entity) {
        String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName().getString());
        if (s != null && "Big Chungus".equals(s)) {
            return BIG_CHUNGUS;
        }
        else {
            return TEXTURE;
        }
    }
}
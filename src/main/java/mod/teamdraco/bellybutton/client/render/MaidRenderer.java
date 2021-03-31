package mod.teamdraco.bellybutton.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.teamdraco.bellybutton.BellyButton;
import mod.teamdraco.bellybutton.client.model.DustBunnyModel;
import mod.teamdraco.bellybutton.entity.DustBunnyEntity;
import mod.teamdraco.bellybutton.entity.MaidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MaidRenderer extends IllagerRenderer<MaidEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/maid.png");

    public MaidRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IllagerModel<>(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
i r
    }

    public ResourceLocation getEntityTexture(MaidEntity entity) {
         return TEXTURE;
    }
}
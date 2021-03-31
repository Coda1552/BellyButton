package teamdraco.bellybutton.client.render;

import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.entity.MaidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MaidRenderer extends IllagerRenderer<MaidEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BellyButton.MOD_ID, "textures/entity/maid.png");

    public MaidRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IllagerModel<>(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new VacuumHeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.entityModel.func_205062_a().showModel = true;
    }

    public ResourceLocation getEntityTexture(MaidEntity entity) {
         return TEXTURE;
    }
}
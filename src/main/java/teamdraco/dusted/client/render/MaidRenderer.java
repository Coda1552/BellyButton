package teamdraco.dusted.client.render;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.dusted.Dusted;
import teamdraco.dusted.common.entities.MaidEntity;

@OnlyIn(Dist.CLIENT)
public class MaidRenderer extends IllagerRenderer<MaidEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Dusted.MOD_ID, "textures/entity/maid.png");
    public static final ModelLayerLocation MAID = new ModelLayerLocation(new ResourceLocation(Dusted.MOD_ID, "maid"), "main");

    public MaidRenderer(EntityRendererProvider.Context manager) {
        super(manager, new IllagerModel<>(manager.bakeLayer(MAID)), 0.5F);

        this.addLayer(new VacuumHeldItemLayer<>(this));
        this.addLayer(new CustomHeadLayer<>(this, manager.getModelSet()));
        this.model.getHat().visible = true;
    }

    public ResourceLocation getTextureLocation(MaidEntity entity) {
         return TEXTURE;
    }
}
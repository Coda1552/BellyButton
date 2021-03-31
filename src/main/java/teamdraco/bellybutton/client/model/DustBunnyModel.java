package teamdraco.bellybutton.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustBunnyModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer ear_left;
    public ModelRenderer ear_right;

    public DustBunnyModel() {
        this.textureWidth = 48;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.body.addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.ear_right = new ModelRenderer(this, 0, 0);
        this.ear_right.mirror = true;
        this.ear_right.setRotationPoint(3.0F, -4.0F, -1.0F);
        this.ear_right.addBox(-1.5F, -8.0F, 0.0F, 3.0F, 8.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ear_right, 0.0F, 0.0F, 0.4363323129985824F);
        this.tail = new ModelRenderer(this, 30, 0);
        this.tail.setRotationPoint(0.0F, 2.0F, 5.0F);
        this.tail.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.ear_left = new ModelRenderer(this, 0, 0);
        this.ear_left.setRotationPoint(-3.0F, -4.0F, -1.0F);
        this.ear_left.addBox(-1.5F, -8.0F, 0.0F, 3.0F, 8.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(ear_left, 0.0F, 0.0F, -0.4363323129985824F);
        this.body.addChild(this.ear_right);
        this.body.addChild(this.tail);
        this.body.addChild(this.ear_left);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
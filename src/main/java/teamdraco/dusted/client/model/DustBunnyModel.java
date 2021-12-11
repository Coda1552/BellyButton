package teamdraco.dusted.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustBunnyModel<T extends Entity> extends EntityModel<T> {
    public ModelPart body;
    public ModelPart tail;
    public ModelPart ear_left;
    public ModelPart ear_right;

    public DustBunnyModel(ModelPart root) {
        this.body = root.getChild("body");
        this.ear_right = this.body.getChild("ear_right");
        this.tail = this.body.getChild("tail");
        this.ear_left = this.body.getChild("ear_left");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition ear_right = body.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(0, 2).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 8.0F, 1.0F, true), PartPose.offsetAndRotation(3.0F, -4.0F, -1.0F, 0.0F, 0.0F, 0.43633232F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(80, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition ear_left = body.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 8.0F, 1.0F, false), PartPose.offsetAndRotation(-3.0F, -4.0F, -1.0F, 0.0F, 0.0F, -0.43633232F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
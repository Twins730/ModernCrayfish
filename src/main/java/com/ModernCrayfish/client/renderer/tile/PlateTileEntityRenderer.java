package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.objects.tileEntity.PlateTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class PlateTileEntityRenderer extends TileEntityRenderer<PlateTileEntity> {

    public PlateTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public boolean shouldRenderOffScreen(PlateTileEntity plateTileEntity) {
        return true;
    }

    @Override
    public void render(PlateTileEntity plateTileEntity, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
        p_225616_3_.pushPose();
        p_225616_3_.translate(0.5,0.12,0.5);
        p_225616_3_.scale(0.5f,0.5f,0.5f);
        p_225616_3_.mulPose(Vector3f.XP.rotationDegrees(90));
        p_225616_3_.mulPose(Vector3f.ZP.rotationDegrees(plateTileEntity.itemDirection.toYRot()));
        if(!plateTileEntity.items.get(0).isEmpty()) {
            Minecraft.getInstance().getItemRenderer().renderStatic(plateTileEntity.items.get(0), ItemCameraTransforms.TransformType.NONE, p_225616_5_, p_225616_6_, p_225616_3_, p_225616_4_);
        }
        p_225616_3_.popPose();
    }
}
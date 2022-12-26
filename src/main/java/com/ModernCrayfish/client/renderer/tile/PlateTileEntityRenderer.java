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
    public boolean isGlobalRenderer(PlateTileEntity p_188185_1_) {
        return true;
    }

    @Override
    public void render(PlateTileEntity plateTileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
        if(!plateTileEntity.items.get(0).isEmpty()) {
            stack.push();
            stack.translate(0.5, 0.12, 0.5);
            stack.scale(0.5f, 0.5f, 0.5f);
            stack.rotate(Vector3f.XP.rotationDegrees(90));
            stack.rotate(Vector3f.ZP.rotationDegrees(plateTileEntity.itemDirection.getHorizontalAngle()));
            Minecraft.getInstance().getItemRenderer().renderItem(plateTileEntity.items.get(0), ItemCameraTransforms.TransformType.NONE, p_225616_5_, p_225616_6_, stack, buffer);
            stack.pop();
        }
    }
}

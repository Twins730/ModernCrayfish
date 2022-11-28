package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.objects.tileEntity.ToasterTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ToasterTileEntityRenderer extends TileEntityRenderer<ToasterTileEntity> {

    public ToasterTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ToasterTileEntity tileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
        if(!tileEntity.items.get(0).isEmpty()){
            stack.pushPose();

            if(tileEntity.itemDirection == Direction.EAST || Direction.WEST == tileEntity.itemDirection){
                stack.translate(0.55, tileEntity.toasting ? 0.3 : 0.4, 0.5);
                stack.scale(0.5f, 0.5f, 0.5f);
                stack.mulPose(Vector3f.YP.rotationDegrees(Direction.WEST.toYRot()));

            }
            if(tileEntity.itemDirection == Direction.NORTH || Direction.SOUTH == tileEntity.itemDirection){
                stack.translate(0.5, tileEntity.toasting ? 0.3 : 0.4, 0.55);
                stack.scale(0.5f, 0.5f, 0.5f);
                stack.mulPose(Vector3f.YP.rotationDegrees(Direction.SOUTH.toYRot()));
            }
            Minecraft.getInstance().getItemRenderer().renderStatic(tileEntity.items.get(0), ItemCameraTransforms.TransformType.NONE, p_225616_5_, p_225616_6_, stack, buffer);
            stack.popPose();
        }

        if(!tileEntity.items.get(1).isEmpty()) {
            stack.pushPose();

            if(tileEntity.itemDirection == Direction.EAST || Direction.WEST == tileEntity.itemDirection){
                stack.translate(0.45, tileEntity.toasting ? 0.3 : 0.4, 0.5);
                stack.scale(0.5f, 0.5f, 0.5f);
                stack.mulPose(Vector3f.YP.rotationDegrees(Direction.WEST.toYRot()));

            }

            if(tileEntity.itemDirection == Direction.NORTH || Direction.SOUTH == tileEntity.itemDirection){
                stack.translate(0.5, tileEntity.toasting ? 0.3 : 0.4, 0.45);
                stack.scale(0.5f, 0.5f, 0.5f);
                stack.mulPose(Vector3f.YP.rotationDegrees(Direction.SOUTH.toYRot()));
            }

            Minecraft.getInstance().getItemRenderer().renderStatic(tileEntity.items.get(1), ItemCameraTransforms.TransformType.NONE, p_225616_5_, p_225616_6_, stack, buffer);
            stack.popPose();
        }
    }
}

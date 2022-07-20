package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.init.ItemInit;
import com.ModernCrayfish.objects.tileEntity.CeilingFanTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class CeilingFanTileEntityRenderer extends TileEntityRenderer<CeilingFanTileEntity> {

    public CeilingFanTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public boolean shouldRenderOffScreen(CeilingFanTileEntity tileEntity) {
        return true;
    }

    @Override
    public void render(CeilingFanTileEntity tileEntity, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
        p_225616_3_.pushPose();
        p_225616_3_.translate(0.5,0.5,0.5);
        float rotation = tileEntity.prevFanRotation + (tileEntity.fanRotation - tileEntity.prevFanRotation) * Minecraft.getInstance().getDeltaFrameTime();
        p_225616_3_.mulPose(Vector3f.YP.rotationDegrees(-rotation));
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(ItemInit.CEILING_FAN_FAN.get()), ItemCameraTransforms.TransformType.NONE,p_225616_5_,p_225616_6_,p_225616_3_,p_225616_4_);
        p_225616_3_.popPose();
    }
}

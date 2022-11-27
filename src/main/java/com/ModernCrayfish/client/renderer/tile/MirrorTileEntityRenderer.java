package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.objects.tileEntity.CeilingFanTileEntity;
import com.ModernCrayfish.objects.tileEntity.MirrorTileEntity;
import com.ModernCrayfish.util.MirrorRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.BlockPos;

//TODO FIX this mess
public class MirrorTileEntityRenderer  //extends TileEntityRenderer<MirrorTileEntity> {
{
    private static MirrorRenderer reflection = new MirrorRenderer(Minecraft.getInstance(),Minecraft.getInstance().renderBuffers());

    public MirrorTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
      //  super(dispatcher);

        //this.reflection = new Framebuffer(32,32,true, Minecraft.ON_OSX);
    }

    //@Override
    public void render(MirrorTileEntity tileEntity, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
        p_225616_3_.pushPose();




        p_225616_3_.popPose();
    }
}

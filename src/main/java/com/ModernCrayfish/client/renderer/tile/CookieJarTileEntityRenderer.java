package com.ModernCrayfish.client.renderer.tile;

import com.ModernCrayfish.objects.tileEntity.CookieJarTileEntity;
import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

public class CookieJarTileEntityRenderer extends TileEntityRenderer<CookieJarTileEntity> {

    public CookieJarTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(CookieJarTileEntity tileEntity, float p_225616_2_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) {
        AtomicDouble offset = new AtomicDouble(0.06);
        tileEntity.items.forEach((item)-> {
            stack.push();
            stack.translate(0.5, offset.get(), 0.5);
            stack.scale(0.45f, 0.5f, 0.45f);
            stack.rotate(Vector3f.XP.rotationDegrees(90));
            Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.NONE, p_225616_5_, p_225616_6_, stack, buffer);
            stack.pop();
            offset.set(offset.get() + 0.09);
        });
    }
}

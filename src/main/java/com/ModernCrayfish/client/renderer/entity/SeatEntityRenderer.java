package com.ModernCrayfish.client.renderer.entity;

import com.ModernCrayfish.objects.entity.SeatEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SeatEntityRenderer extends EntityRenderer<SeatEntity> {

    public SeatEntityRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity seatEntity) {
        return null;
    }
}

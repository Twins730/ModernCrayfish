package com.ModernCrayfish.client.renderer.entity;

import com.ModernCrayfish.objects.entity.MirrorEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MirrorEntityRenderer extends EntityRenderer<MirrorEntity> {

    public MirrorEntityRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(MirrorEntity entity) {
        return null;
    }
}

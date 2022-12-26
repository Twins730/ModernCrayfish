package com.ModernCrayfish.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class MirrorRenderer extends WorldRenderer {

    public MirrorRenderer(Minecraft p_i225967_1_, RenderTypeBuffers p_i225967_2_) {
        super(p_i225967_1_, p_i225967_2_);
    }

    @Override
    public void renderClouds(MatrixStack p_228425_1_, float p_228425_2_, double p_228425_3_, double p_228425_5_, double p_228425_7_) {
        super.renderClouds(p_228425_1_, p_228425_2_, p_228425_3_, p_228425_5_, p_228425_7_);
    }

    @Override
    public void playRecord(@Nullable SoundEvent p_184377_1_, BlockPos p_184377_2_, @Nullable MusicDiscItem musicDiscItem) {

    }

    @Override
    public void playEvent(PlayerEntity p_180439_1_, int p_180439_2_, BlockPos p_180439_3_, int p_180439_4_) {

    }

    @Override
    public void playRecord(@Nullable SoundEvent p_184377_1_, BlockPos p_184377_2_) {

    }

}

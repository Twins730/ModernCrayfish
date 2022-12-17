package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.client.renderer.tile.MirrorTileEntityRenderer;
import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.blocks.MirrorBlock;
import com.ModernCrayfish.objects.entity.MirrorEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MirrorTileEntity extends TileEntity
{

    private MirrorEntity bindedMirror = null;

    public MirrorTileEntity() {
        super(TileInit.MIRROR_TILE.get());
    }

    @OnlyIn(Dist.CLIENT)
    public MirrorEntity getMirror()
    {
        if(bindedMirror == null)
        {
            Direction facing = level.getBlockState(worldPosition).getValue(MirrorBlock.FACING);
            bindedMirror = new MirrorEntity(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), facing);
            level.addFreshEntity(bindedMirror);
        }
        return bindedMirror;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onChunkUnloaded()
    {
        if(bindedMirror != null)
        {
            MirrorTileEntityRenderer.removeRegisteredMirror(bindedMirror);
            bindedMirror.kill();
            bindedMirror = null;
        }
    }



}

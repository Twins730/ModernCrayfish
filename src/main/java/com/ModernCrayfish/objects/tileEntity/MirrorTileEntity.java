package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.client.renderer.tile.MirrorTileEntityRenderer;
import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.blocks.MirrorBlock;
import com.ModernCrayfish.objects.entity.MirrorEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MirrorTileEntity extends TileEntity {

    private MirrorEntity boundMirror = null;

    public MirrorTileEntity() {
        super(TileInit.MIRROR_TILE.get());
    }

    @OnlyIn(Dist.CLIENT)
    public MirrorEntity getMirror()
    {
        if(boundMirror == null)
        {
            Direction facing = world.getBlockState(pos).get(MirrorBlock.FACING);
            boundMirror = new MirrorEntity(world, pos.getX(), pos.getY(), pos.getZ(), facing);
            world.addEntity(boundMirror);
        }
        return boundMirror;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onChunkUnloaded()
    {
        if(boundMirror != null)
        {
            //MirrorTileEntityRenderer.removeRegisteredMirror(boundMirror);
            boundMirror.remove();
            boundMirror = null;
        }
    }

    public BlockPos pos(){
        return pos;
    }



}

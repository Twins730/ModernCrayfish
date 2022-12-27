package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.BlockInit;
import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.blocks.CeilingFanBlock;
import com.ModernCrayfish.objects.blocks.CeilingLightBlock;
import com.ModernCrayfish.objects.blocks.ModernLightBlock;
import com.ModernCrayfish.objects.blocks.Switchable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class LightSwitchTileEntity extends TileEntity {

    private final List<BlockPos> lights = NonNullList.create();

    public LightSwitchTileEntity() {
        super(TileInit.LIGHT_SWITCH_TILE.get());
    }

    public void use(World world) {
        /*
        targets.forEach(target->{
            ModernCrayfish.LOGGER.info("REEEEEEEEEEE @"+target.toString());
            if(world.getBlockState(target).getBlock() instanceof CeilingLightBlock){
                world.notifyBlockUpdate(target,world.getBlockState(target),world.getBlockState(target).func_235896_a_(CeilingLightBlock.LIT),3);
             //   world.neighborChanged(target,getBlockState().getBlock(),pos);
            }

            if(world.getBlockState(target).getBlock() instanceof CeilingFanBlock){
                world.notifyBlockUpdate(target,world.getBlockState(target),world.getBlockState(target).func_235896_a_(CeilingFanBlock.POWERED),3);
               // world.getBlockState(target).func_235896_a_(CeilingFanBlock.POWERED);
                //world.neighborChanged(target,getBlockState().getBlock(),pos);
            }
        });

         */
    }

    public void setState(boolean powered) {
        lights.removeIf(lightPos ->
        {
            BlockState state = world.getBlockState(lightPos);
            return !(state.getBlock() instanceof Switchable);
        });
        lights.forEach(pos1 -> {
            assert world != null;
            BlockState state = world.getBlockState(pos1);
            if (state.getBlock() instanceof CeilingLightBlock) {
                world.setBlockState(pos1, BlockInit.CEILING_LIGHT.get().getDefaultState().with(CeilingLightBlock.LIT, powered));
            }
            if (state.getBlock() instanceof CeilingFanBlock) {
                world.setBlockState(pos1, BlockInit.CEILING_FAN.get().getDefaultState().with(CeilingFanBlock.POWERED, powered));
            }
            if (state.getBlock() instanceof ModernLightBlock) {
                Direction facing = world.getBlockState(pos1).get(ModernLightBlock.FACING);
                world.setBlockState(pos1, BlockInit.MODERN_LIGHT.get().getDefaultState().with(ModernLightBlock.LIT, powered).with(ModernLightBlock.FACING,facing));
            }
        });
    }


    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        lights.forEach(blockPos -> list.add(LongNBT.valueOf(blockPos.toLong())));
        nbt.put("lights",list);

        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        lights.clear();
        ListNBT tagList = nbt.getList("lights",Constants.NBT.TAG_LONG);
        tagList.forEach(nbtBase -> addLight(((LongNBT)nbtBase).getLong()));
        super.read(state, nbt);
    }


    private void addLight(long pos)
    {
        BlockPos lightPos = BlockPos.fromLong(pos);
        if(!lights.contains(lightPos))
        {
            lights.add(lightPos);
        }
    }
}

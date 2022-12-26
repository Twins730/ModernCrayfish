package com.ModernCrayfish.objects.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LightSwitchItem extends BlockItem {

    public List<BlockPos> targets = new ArrayList<>();

    public LightSwitchItem(Block block, Properties item) {
        super(block,item);
    }


    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        if(context.getPlayer().isSneaking()){
            ItemStack itemstack = context.getItem();
            CompoundNBT nbt = new CompoundNBT();
            targets.add(context.getPos());
            List<Integer> xPoses = new ArrayList<>();
            List<Integer> yPoses = new ArrayList<>();
            List<Integer> zPoses = new ArrayList<>();
            this.targets.forEach((target)->{
                xPoses.add(target.getX());
                yPoses.add(target.getY());
                zPoses.add(target.getZ());
            });
            nbt.putIntArray("xPoses", xPoses);
            nbt.putIntArray("yPoses", yPoses);
            nbt.putIntArray("zPoses", zPoses);
            itemstack.setTagInfo("BlockEntityTag",nbt);

            return false;
        }
        return super.placeBlock(context,state);
    }


}

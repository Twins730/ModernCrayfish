package com.ModernCrayfish.objects.item;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.init.BlockInit;
import com.ModernCrayfish.objects.blocks.Switchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LightSwitchItem extends BlockItem {

    public LightSwitchItem(Block block, Properties item) {
        super(block,item);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if(player.isSneaking())
        {
            heldItem.setTag(null);
            return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
        }
        return new ActionResult<>(ActionResultType.PASS, heldItem);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        World world = context.getWorld();
        BlockPos pos = context.getPos();


        ItemStack heldItem = player.getHeldItem(hand);
        if(!player.isSneaking())
        {
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof Switchable)
            {
                this.addLight(heldItem, pos);
                return ActionResultType.SUCCESS;
            }
        }

        ListNBT lights = getLights(heldItem);
        if(lights != null)
        {
            for(int i = 0; i < lights.size(); i++)
            {
                INBT nbtBase = lights.get(i);
                BlockPos lightPos = BlockPos.fromLong(((LongNBT) nbtBase).getLong());
                BlockPos placedPos = pos.offset(context.getFace());
                double distance = Math.sqrt(lightPos.distanceSq(placedPos.getX() + 0.5, placedPos.getY() + 0.5, placedPos.getZ() + 0.5,true));
                if(distance > 16)
                {
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
    }

    private void addLight(ItemStack stack, BlockPos pos)
    {
        CompoundNBT tagCompound = createTag(stack);
        if(!tagCompound.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND))
        {
            tagCompound.put("BlockEntityTag", new CompoundNBT());
        }

        CompoundNBT entityTagCompound = tagCompound.getCompound("BlockEntityTag");
        if(!entityTagCompound.contains("lights", Constants.NBT.TAG_LIST))
        {
            entityTagCompound.put("lights", new ListNBT());
        }

        ListNBT tagList = (ListNBT) entityTagCompound.get("lights");
        if(!containsLight(tagList, pos))
        {
            tagList.add(LongNBT.valueOf(pos.toLong()));
        }
    }

    private boolean containsLight(ListNBT tagList, BlockPos pos)
    {
        for(int i = 0; i < tagList.size(); i++)
        {
            LongNBT tagLong = (LongNBT) tagList.get(i);
            if(tagLong.getLong() == pos.toLong())
            {
                return true;
            }
        }
        return false;
    }


    @Nullable
    private ListNBT getLights(ItemStack stack)
    {
        CompoundNBT tagCompound = createTag(stack);
        CompoundNBT entityTagCompound = tagCompound.getCompound("BlockEntityTag");
        if(entityTagCompound.get("lights") != null)
        {
            return entityTagCompound.getList("lights", Constants.NBT.TAG_COMPOUND);
        }

        return null;
    }

    private CompoundNBT createTag(ItemStack stack)
    {
        if(!stack.hasTag())
        {
            stack.setTag(new CompoundNBT());
        }
        return stack.getTag();
    }
}

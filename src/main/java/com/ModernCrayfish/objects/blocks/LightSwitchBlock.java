package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;

import java.util.Map;

public class LightSwitchBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty NORTH = FourWayBlock.NORTH;
    public static final BooleanProperty EAST = FourWayBlock.EAST;
    public static final BooleanProperty SOUTH = FourWayBlock.SOUTH;
    public static final BooleanProperty WEST = FourWayBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final BooleanProperty ON = BooleanProperty.create("on");

    public LightSwitchBlock(Properties p_i48402_1_) {
        super(p_i48402_1_);
    }



    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }
}

package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

public class LampBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    public LampBlock(Properties properties){
        super(properties);
        this.setDefaultState(this.getDefaultState().with(UP, false).with(DOWN, false).with(LIT, false).with(WATERLOGGED,false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(UP).add(DOWN).add(WATERLOGGED);

    }
}

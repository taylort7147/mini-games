package com.github.taylort7147.minigames;

import net.minecraft.util.math.BlockPos;

public class PlayerInfo
{
    private BlockPos lastPos;
    
    public PlayerInfo()
    {
        this.lastPos = null;
    }
    
    public void setLastPos(BlockPos pos)
    {
        this.lastPos = pos;
    }
    
    public BlockPos getLastPos()
    {
        return this.lastPos;
    }
}

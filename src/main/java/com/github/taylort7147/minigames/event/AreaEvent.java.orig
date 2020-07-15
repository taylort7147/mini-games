package com.github.taylort7147.minigames.event;

import com.github.taylort7147.minigames.Area;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public abstract class AreaEvent extends Event
{
    public static class BlockPosSelected extends AreaEvent
    {
        private PlayerEntity player;
        private BlockPos pos;

        public BlockPosSelected(PlayerEntity player, BlockPos pos)
        {
            this.player = player;
            this.pos = pos;
        }
        
        public PlayerEntity getPlayer()
        {
            return this.player;
        }
        
        public BlockPos getPos()
        {
            return this.pos;
        }
    }
    
    public static class AreaEntered extends AreaEvent
    {
        private PlayerEntity player;
        private Area area;
        
        public AreaEntered(PlayerEntity player, Area area)
        {
            this.player = player;
            this.area = area;
        }
        
        public PlayerEntity getPlayer()
        {
            return this.player;
        }
        
        public Area getArea()
        {
            return this.area;
        }
    }
    
    public static class AreaLeft extends AreaEvent
    {
        private PlayerEntity player;
        private Area area;
        
        public AreaLeft(PlayerEntity player, Area area)
        {
            this.player = player;
            this.area = area;
        }
        
        public PlayerEntity getPlayer()
        {
            return this.player;
        }
        
        public Area getArea()
        {
            return this.area;
        }
    }
}

package com.github.taylort7147.minigames;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;

public interface IAreaManager
{
    @Nullable
    public Area getArea(BlockPos pos);
    
    public boolean add(@Nonnull Area area);
    
    public boolean remove(@Nonnull Area area);
    
    public List<Area> getAreas();
}

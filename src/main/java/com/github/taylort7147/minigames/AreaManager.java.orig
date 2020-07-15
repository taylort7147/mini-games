package com.github.taylort7147.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.util.math.BlockPos;

public class AreaManager implements IAreaManager
{
    private List<Area> areas;

    public AreaManager()
    {
        this.areas = new ArrayList<Area>();
    }

    @Override
    public Area getArea(BlockPos pos)
    {
        Optional<Area> area = areas.stream().filter(a -> a.isInArea2D(pos)).findFirst();
        return area.orElse(null);
    }

    @Override
    public boolean add(Area area)
    {
        if (area == null)
        {
            throw new NullPointerException("Cannot add a null area");
        }

        return areas.add(area);
    }

    @Override
    public boolean remove(Area area)
    {
        if (area == null)
        {
            throw new NullPointerException("Cannot remove a null area");
        }

        return areas.remove(area);
    }

    @Override
    public List<Area> getAreas()
    {
        return new ArrayList<Area>(this.areas);
    }

}

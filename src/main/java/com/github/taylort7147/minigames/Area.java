package com.github.taylort7147.minigames;

import com.github.taylort7147.minigames.util.NbtUtils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class Area implements INBTSerializable<INBT>
{
    BlockPos first;
    BlockPos second;

    private Area()
    {
        this.first = null;
        this.second = null;
    }

    public static Area fromNbt(CompoundNBT nbt)
    {
        Area area = new Area();
        area.deserializeNBT(nbt);
        return area;
    }

    public Area(BlockPos first, BlockPos second)
    {
        this.first = first;
        this.second = second;
    }

    boolean isInArea2D(BlockPos pos)
    {
        int minX = Math.min(this.first.getX(), this.second.getX());
        int maxX = Math.max(this.first.getX(), this.second.getX());
        int minY = Math.min(this.first.getY(), this.second.getY());
        int maxY = Math.max(this.first.getY(), this.second.getY());

        if (pos.getX() < minX)
            return false;
        if (pos.getX() > maxX)
            return false;
        if (pos.getY() < minY)
            return false;
        if (pos.getY() > maxY)
            return false;
        return true;
    }

    boolean isInArea3D(BlockPos pos)
    {
        if (!isInArea2D(pos))
            return false;

        int minZ = Math.min(this.first.getZ(), this.second.getZ());
        int maxZ = Math.max(this.first.getZ(), this.second.getZ());

        if (pos.getZ() < minZ)
            return false;
        if (pos.getZ() > maxZ)
            return false;
        return true;
    }

    @Override
    public INBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("First", NbtUtils.serializeBlockPos(this.first));
        nbt.put("Second", NbtUtils.serializeBlockPos(this.second));
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        if (!(nbt instanceof CompoundNBT))
            throw new IllegalArgumentException("NBT is not a CompoundNBT");

        CompoundNBT compoundNbt = (CompoundNBT) nbt;
        this.first = NbtUtils.deserializeBlockPos(compoundNbt.getCompound("First"));
        this.second = NbtUtils.deserializeBlockPos(compoundNbt.getCompound("Second"));
    }
}

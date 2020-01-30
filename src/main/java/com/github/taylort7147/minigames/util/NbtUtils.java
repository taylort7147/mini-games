package com.github.taylort7147.minigames.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public class NbtUtils
{
    public static CompoundNBT serializeBlockPos(BlockPos pos)
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("X", pos.getX());
        nbt.putInt("Y", pos.getY());
        nbt.putInt("Z", pos.getZ());
        return nbt;
    }
    
    public static BlockPos deserializeBlockPos(CompoundNBT nbt)
    {
        final int x = nbt.getInt("X");
        final int y = nbt.getInt("Y");
        final int z = nbt.getInt("Z");
        return new BlockPos(x, y, z);
    }
}

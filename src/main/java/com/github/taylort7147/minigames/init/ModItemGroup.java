package com.github.taylort7147.minigames.init;

import java.util.function.Supplier;

import com.github.taylort7147.minigames.MiniGames;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroup extends ItemGroup
{

    public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroup(MiniGames.MODID, () -> new ItemStack(Items.DIRT));

    private final Supplier<ItemStack> mIconSupplier;

    public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier)
    {
        super(name);
        mIconSupplier = iconSupplier;
    }

    @Override
    public ItemStack createIcon()
    {
        return mIconSupplier.get();
    }
}

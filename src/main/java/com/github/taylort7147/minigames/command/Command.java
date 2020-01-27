package com.github.taylort7147.minigames.command;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;

public abstract class Command
{
    protected String name;

    public Command(String name)
    {
        this.name = name;
    }

    public final String getName()
    {
        return name;
    }

    public abstract int handle(CommandContext<CommandSource> context);
}

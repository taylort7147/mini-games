package com.github.taylort7147.minigames.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class EndGameCommand extends Command
{

    public EndGameCommand(String name)
    {
        super(name);
    }

    @Override
    public int handle(CommandContext<CommandSource> context)
    {
        try
        {
            context.getSource().asPlayer().sendMessage(new StringTextComponent("Ending game"));
            return 0;
        } catch (CommandSyntaxException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}

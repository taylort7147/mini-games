package com.github.taylort7147.minigames.command;

import com.github.taylort7147.minigames.event.AreaEvent;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class AreaSelectCommand extends Command
{

    public AreaSelectCommand(String name)
    {
        super(name);
    }

    @Override
    public int handle(CommandContext<CommandSource> context)
    {
        try
        {
            PlayerEntity player = context.getSource().asPlayer();
            World world = context.getSource().getWorld();
            MinecraftForge.EVENT_BUS.post(new AreaEvent.BlockPosSelected(player, player.getPosition()));
            player.sendMessage(
                    new StringTextComponent("Selecting block position " + player.getPosition() + " for area"));
            return 0;
        } catch (CommandSyntaxException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}

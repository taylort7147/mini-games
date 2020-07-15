package com.github.taylort7147.minigames.command;

import com.github.taylort7147.minigames.Area;
import com.github.taylort7147.minigames.IAreaManager;
import com.github.taylort7147.minigames.capability.CapabilityAreaManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentAreaCommand extends Command
{

    public CurrentAreaCommand(String name)
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
            LazyOptional<IAreaManager> cap = world.getCapability(CapabilityAreaManager.INSTANCE);

            if (!cap.isPresent())
            {
                player.sendMessage(new StringTextComponent("CapabilityAreaManager is not present"));
            }
            cap.ifPresent(areaManager -> {
                Area area = areaManager.getArea(player.getPosition());
                player.sendMessage(new StringTextComponent(area.toString()));
            });
            return 0;
        } catch (CommandSyntaxException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}

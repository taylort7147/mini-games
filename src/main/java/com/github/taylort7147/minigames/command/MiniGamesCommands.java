package com.github.taylort7147.minigames.command;

import com.github.taylort7147.minigames.MiniGames;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class MiniGamesCommands
{
    private static final String PLAYER_COMMANDS_NODE = MiniGames.MODID + ".commands.player";
    private static final String OP_COMMANDS_NODE = MiniGames.MODID + ".commands.op";

    public static void registerNodes()
    {
        PermissionAPI.registerNode(PLAYER_COMMANDS_NODE, DefaultPermissionLevel.ALL, "Commands for all players");
        PermissionAPI.registerNode(OP_COMMANDS_NODE, DefaultPermissionLevel.OP, "Commands for OPs");
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher)
    {
        registerCommand(dispatcher, new StartGameCommand("startgame"), PLAYER_COMMANDS_NODE);
        registerCommand(dispatcher, new EndGameCommand("endgame"), PLAYER_COMMANDS_NODE);
        registerCommand(dispatcher, new AreaSelectCommand("areaselect"), PLAYER_COMMANDS_NODE);
        registerCommand(dispatcher, new CurrentAreaCommand("currentarea"), PLAYER_COMMANDS_NODE);
    }

    private static void registerCommand(CommandDispatcher<CommandSource> dispatcher, Command command, String node)
    {
        MiniGames.LOGGER
                .debug("Registering command \"" + command.getName() + "\" of type " + command.getClass().getName());
        dispatcher.register(Commands.literal(command.getName()).requires(source -> {
            try
            {
                return PermissionAPI.hasPermission(source.asPlayer(), node);
            } catch (CommandSyntaxException e)
            {
                e.printStackTrace();
            }
            return false;
        }).executes(command::handle));
    }
}

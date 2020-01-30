package com.github.taylort7147.minigames;

import com.github.taylort7147.minigames.capability.CapabilityAreaManager;
import com.github.taylort7147.minigames.command.MiniGamesCommands;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class ForgeEventSubscriber
{
    @SubscribeEvent
    public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event)
    {
        MiniGames.LOGGER.debug("onAttachWorldCapabilities");
        event.addCapability(new ResourceLocation(MiniGames.MODID, "area_manager"),
                new CapabilityAreaManager.Provider());
    }

    @SubscribeEvent
    public static void onServerSetup(FMLServerStartingEvent event)
    {
        MiniGames.LOGGER.debug("onServerSetup");

        MiniGames.LOGGER.info("Registering permission nodes");
        MiniGamesCommands.registerNodes();

        MiniGames.LOGGER.info("Registering commands");
        MiniGamesCommands.registerCommands(event.getServer().getCommandManager().getDispatcher());

    }
}

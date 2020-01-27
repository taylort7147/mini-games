package com.github.taylort7147.minigames;

import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class ForgeEventSubscriber
{
    @SubscribeEvent
    public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event)
    {
        MiniGames.LOGGER.debug("onAttachWorldCapabilities");
    }
}

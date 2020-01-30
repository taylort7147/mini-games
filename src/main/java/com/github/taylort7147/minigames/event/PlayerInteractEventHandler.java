package com.github.taylort7147.minigames.event;

import com.github.taylort7147.minigames.MiniGames;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MiniGames.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class PlayerInteractEventHandler
{
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        MiniGames.LOGGER.debug("onPlayerRightClickBlock");

    }

    @SubscribeEvent
    public static void onPlayerUse(PlayerInteractEvent.RightClickItem event)
    {
        MiniGames.LOGGER.debug("onPlayerUse");
    }
    
    @SubscribeEvent
    public static void onPlayerMoved(LivingUpdateEvent event)
    {
        
    }
}

package com.github.taylort7147.minigames;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.taylort7147.minigames.capability.CapabilityAreaManager;
import com.github.taylort7147.minigames.event.AreaEvent;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class PlayerWatcher
{
    private static Map<UUID, PlayerInfo> allPlayerInfo = new HashMap<UUID, PlayerInfo>();

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        MiniGames.LOGGER.debug("Adding player " + event.getPlayer().getName().getString() + " to player watch list");
        allPlayerInfo.put(event.getPlayer().getUniqueID(), new PlayerInfo());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
        MiniGames.LOGGER
                .debug("Removing player " + event.getPlayer().getName().getString() + " from player watch list");
        allPlayerInfo.remove(event.getPlayer().getUniqueID());
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!(livingEntity instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) livingEntity;
        PlayerInfo playerInfo = allPlayerInfo.get(player.getUniqueID());
        BlockPos currentPos = player.getPosition();
        BlockPos lastPos = playerInfo.getLastPos();
        if (!currentPos.equals(lastPos))
        {
            World world = player.world;
            world.getCapability(CapabilityAreaManager.INSTANCE).ifPresent(areaManager -> {

                Area lastArea = null;
                if (lastPos != null)
                {
                    lastArea = areaManager.getArea(lastPos);
                }
                Area currentArea = areaManager.getArea(currentPos);
                if (lastArea != currentArea)
                {
                    // Get current area is different than previous area, post event
                    player.sendMessage(new StringTextComponent("Your area changed."));

                    if (lastArea != null)
                    {
                        MiniGames.LOGGER.debug("Player " + player.getName().getString() + " left area " + lastArea);
                        MinecraftForge.EVENT_BUS.post(new AreaEvent.AreaLeft(player, lastArea));
                    }
                    if (currentArea != null)
                    {
                        MiniGames.LOGGER
                                .debug("Player " + player.getName().getString() + " entered area " + currentArea);
                        MinecraftForge.EVENT_BUS.post(new AreaEvent.AreaEntered(player, currentArea));
                    }
                }
            });
            playerInfo.setLastPos(currentPos);
        }
    }
}

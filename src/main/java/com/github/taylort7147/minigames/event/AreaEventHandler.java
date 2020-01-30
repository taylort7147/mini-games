package com.github.taylort7147.minigames.event;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.github.taylort7147.minigames.AccumulatorMap;
import com.github.taylort7147.minigames.Area;
import com.github.taylort7147.minigames.IAreaManager;
import com.github.taylort7147.minigames.MiniGames;
import com.github.taylort7147.minigames.capability.CapabilityAreaManager;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class AreaEventHandler
{
    private static AccumulatorMap<UUID, BlockPos> areaRequests = new AccumulatorMap<UUID, BlockPos>(2);

    @SubscribeEvent
    public static void onAreaEntered(AreaEvent.AreaEntered event)
    {
        MiniGames.LOGGER.debug("onAreaEntered");
    }

    @SubscribeEvent
    public static void onAreaLeft(AreaEvent.AreaLeft event)
    {
        MiniGames.LOGGER.debug("onAreaLeft");
    }

    @SubscribeEvent
    public static void onBlockPosSelected(AreaEvent.BlockPosSelected event)
    {
        MiniGames.LOGGER.debug("onBlockPosSelected");
        Optional<Set<BlockPos>> completedSet = areaRequests.add(event.getPlayer().getUniqueID(), event.getPos());
        if (!completedSet.isPresent())
        {
            MiniGames.LOGGER.debug("Area set is not complete for player " + event.getPlayer().getName().getString());
        } else
        {
            MiniGames.LOGGER.debug("Area set is complete");
        }
        LazyOptional<IAreaManager> areaManagerCapability = event.getPlayer().world
                .getCapability(CapabilityAreaManager.INSTANCE);
        if (!areaManagerCapability.isPresent())
        {
            MiniGames.LOGGER.debug("CapabilityAreaManager is not present");
        }
        areaManagerCapability.ifPresent(areaManager -> {
            completedSet.ifPresent(set -> {
                Iterator<BlockPos> itr = set.iterator();
                BlockPos first = itr.next();
                BlockPos second = itr.next();
                Area area = new Area(first, second);
                areaManager.add(area);
                MiniGames.LOGGER.debug("Added new area " + area);
                event.getPlayer().sendMessage(new StringTextComponent("Created new area"));
            });
        });
    }
}

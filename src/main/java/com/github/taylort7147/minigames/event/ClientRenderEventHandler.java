package com.github.taylort7147.minigames.event;

import com.github.taylort7147.minigames.MiniGames;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.FORGE)
public class ClientRenderEventHandler
{
//    @SubscribeEvent
    public static void onRender(LivingUpdateEvent event)
    {
        Entity entity = event.getEntity();
        if (!(entity instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) entity;
        World world = player.world;

        if (!world.isRemote())
            return;

        BlockPos pos = player.getPosition();
        MiniGames.LOGGER.debug("Pos: " + pos);
        BlockPos below = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
        TileEntity tileEntity = new CampfireTileEntity();
        tileEntity.setPos(below);
        world.setTileEntity(below, tileEntity);
        MiniGames.LOGGER.debug("onRender");
    }
}

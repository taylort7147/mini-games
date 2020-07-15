package com.github.taylort7147.minigames.capability;

import com.github.taylort7147.minigames.Area;
import com.github.taylort7147.minigames.AreaManager;
import com.github.taylort7147.minigames.IAreaManager;
import com.github.taylort7147.minigames.MiniGames;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityAreaManager
{
    @CapabilityInject(IAreaManager.class)
    public static Capability<IAreaManager> INSTANCE = null;

    /**
     * Static helper method for registering this capability
     */
    public static void register()
    {
        MiniGames.LOGGER.debug("Registering " + CapabilityAreaManager.class.getName());
        CapabilityManager.INSTANCE.register(IAreaManager.class, new CapabilityAreaManager.Storage(), AreaManager::new);
    }

    public static class Storage implements Capability.IStorage<IAreaManager>
    {

        @Override
        public INBT writeNBT(Capability<IAreaManager> capability, IAreaManager instance, Direction side)
        {
            ListNBT nbt = new ListNBT();
            MiniGames.LOGGER.debug("CapabilityAreaManager.Storage instance: " + instance);
            instance.getAreas().forEach(a -> nbt.add(a.serializeNBT()));
            return nbt;
        }

        @Override
        public void readNBT(Capability<IAreaManager> capability, IAreaManager instance, Direction side, INBT nbt)
        {
            if (nbt instanceof ListNBT)
            {
                ListNBT listNbt = (ListNBT) nbt;
                listNbt.forEach(areaNbt -> {
                    if (areaNbt instanceof CompoundNBT)
                    {
                        CompoundNBT areaCompoundNbt = (CompoundNBT) areaNbt;

                        Area area = Area.fromNbt(areaCompoundNbt);
                        instance.add(area);
                    } else
                    {
                        MiniGames.LOGGER.error("NBT is not a CompoundNBT");
                    }
                });
            } else
            {
                MiniGames.LOGGER.error("NBT is not a ListNBT");
            }

        }
    }

    /**
     * This class is responsible for providing the capability implementation.
     */
    public static class Provider implements ICapabilitySerializable<INBT>
    {
        /**
         * A LazyOptional to return the default instance
         */
        private LazyOptional<IAreaManager> defaultInstance = LazyOptional
                .of(CapabilityAreaManager.INSTANCE::getDefaultInstance);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
        {
            return cap == CapabilityAreaManager.INSTANCE ? defaultInstance.cast() : LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT()
        {
            return CapabilityAreaManager.INSTANCE.getStorage().writeNBT(CapabilityAreaManager.INSTANCE,
                    defaultInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                    null);
        }

        @Override
        public void deserializeNBT(INBT nbt)
        {
            CapabilityAreaManager.INSTANCE.getStorage().readNBT(CapabilityAreaManager.INSTANCE,
                    defaultInstance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")),
                    null, nbt);

        }
    }
}

package com.github.taylort7147.minigames;

import javax.annotation.Nonnull;

import com.github.taylort7147.minigames.command.MiniGamesCommands;
import com.github.taylort7147.minigames.init.ModItemGroup;
import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = MiniGames.MODID, bus = Bus.MOD)
public final class ModEventSubscriber
{

    /**
     * Event handler for registering all mod items
     * 
     * @param event
     */
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event)
    {
        MiniGames.LOGGER.debug("onRegisterItems");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (final Block block : ForgeRegistries.BLOCKS.getValues())
        {
            registerBlockItemForBlock(registry, block);
        }
    }

    /**
     * Helper for registering a block item for a block with a default-generated
     * BlockItem
     * 
     * @param registry
     * @param block
     */
    private static void registerBlockItemForBlock(IForgeRegistry<Item> registry, final Block block)
    {
        final ResourceLocation blockRegistryName = block.getRegistryName();
        // An extra safe-guard against badly registered blocks
        Preconditions.checkNotNull(blockRegistryName, "Registry Name of block \"" + block + "\" of class \""
                + block.getClass().getName() + "\"is null! This is not allowed!");

        if (!blockRegistryName.getNamespace().equals(MiniGames.MODID))
        {
            return;
        }

        final Item.Properties properties = new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP);
        final BlockItem blockItem = new BlockItem(block, properties);
        registry.register(setUp(blockItem, blockRegistryName));
    }

    /**
     * Event handler for registering all mod blocks
     * 
     * @param event
     */
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
    {
        MiniGames.LOGGER.debug("onRegisterBlocks");
    }

    /**
     * Event handler for registering all mod tile entity types
     * 
     * @param event
     */
    @SubscribeEvent
    public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event)
    {
        MiniGames.LOGGER.debug("onRegisterTileEntityTypes");
    }

    /**
     * Generic helper for setting the registry name on an object under the mod
     * namespace
     * 
     * @param <T>
     * @param entry The object being registered
     * @param name  The un-namespaced name of the object being registered
     * @return
     */
    public static <T extends IForgeRegistryEntry<T>> T setUp(final T entry, final String name)
    {
        return setUp(entry, new ResourceLocation(MiniGames.MODID, name));
    }

    /**
     * Generic helper for setting the registry name on an object under the mod
     * namespace
     * 
     * @param <T>
     * @param entry        The object being registered
     * @param registryName The resource location of the object being registered
     * @return
     */
    public static <T extends IForgeRegistryEntry<T>> T setUp(final T entry, final ResourceLocation registryName)
    {
        entry.setRegistryName(registryName);
        return entry;
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        MiniGames.LOGGER.debug("onCommonSetup");
    }
}

package net.sewn404.blockxp.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.sewn404.blockxp.Blockxp;
import net.sewn404.blockxp.block.XPOreBlock;

public class ModBlocks {
    public static final Block XP_ORE = registerBlock("xp_ore",
            new XPOreBlock(Block.Settings.create()
                    .strength(1.5f, 1.0f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    private static Block registerBlock(String name, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        registerBlockItem(name, blockItem);
        return Registry.register(Registries.BLOCK, createIdentifier(name), block);
    }

    private static void registerBlockItem(String name, BlockItem blockItem) {
        Registry.register(Registries.ITEM, createIdentifier(name), blockItem);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.add(blockItem);
        });
    }

    private static Identifier createIdentifier(String path) {
        return Identifier.of(Blockxp.MOD_ID, path);
    }

    public static void registerModBlocks() {
        Blockxp.LOGGER.info("Registering ModBlocks for " + Blockxp.MOD_ID);
    }
}
package net.sewn404.blockxp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.sewn404.blockxp.registry.ModBlocks;
import net.sewn404.blockxp.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class Blockxp implements ModInitializer {
    public static final String MOD_ID = "blockxp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Configuration
    private static final int XP_PER_BLOCK = 1;

    // Pickaxe chance percentages
    private static final int WOOD_PICKAXE_CHANCE = 20;
    private static final int STONE_PICKAXE_CHANCE = 30;
    private static final int IRON_PICKAXE_CHANCE = 40;
    private static final int GOLD_PICKAXE_CHANCE = 60;
    private static final int DIAMOND_PICKAXE_CHANCE = 50;
    private static final int NETHERITE_PICKAXE_CHANCE = 55;
    private static final int DEFAULT_PICKAXE_CHANCE = 10;

    private static final Random RANDOM = new Random();

    @Override
    public void onInitialize() {
        // Register mod blocks
        ModBlocks.registerModBlocks();
        // Add this line:
        ModOreGeneration.generateOres();
        
        LOGGER.info("Initializing {} - XP chance now varies by pickaxe type!", MOD_ID);

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world instanceof ServerWorld) {
                String blockId = Registries.BLOCK.getId(state.getBlock()).toString();

                // Check if it's either a vanilla block or our custom XP ore
                if (blockId.startsWith("minecraft:") || blockId.equals(MOD_ID + ":xp_ore")) {
                    ItemStack mainHandStack = player.getMainHandStack();

                    if (mainHandStack.getItem() instanceof PickaxeItem) {
                        MiningToolItem tool = (MiningToolItem) mainHandStack.getItem();
                        ToolMaterial material = tool.getMaterial();

                        int currentChance = DEFAULT_PICKAXE_CHANCE;

                        if (material == ToolMaterials.WOOD) {
                            currentChance = WOOD_PICKAXE_CHANCE;
                        } else if (material == ToolMaterials.STONE) {
                            currentChance = STONE_PICKAXE_CHANCE;
                        } else if (material == ToolMaterials.IRON) {
                            currentChance = IRON_PICKAXE_CHANCE;
                        } else if (material == ToolMaterials.GOLD) {
                            currentChance = GOLD_PICKAXE_CHANCE;
                        } else if (material == ToolMaterials.DIAMOND) {
                            currentChance = DIAMOND_PICKAXE_CHANCE;
                        } else if (material == ToolMaterials.NETHERITE) {
                            currentChance = NETHERITE_PICKAXE_CHANCE;
                        }

                        // Always drop XP for our custom XP ore, otherwise use chance
                        if (blockId.equals(MOD_ID + ":xp_ore") || RANDOM.nextInt(100) < currentChance) {
                            int xpAmount = blockId.equals(MOD_ID + ":xp_ore") ? 5 : XP_PER_BLOCK;
                            player.addExperience(xpAmount);
                        }
                    }
                }
            }
        });

        LOGGER.info("{} initialized successfully!", MOD_ID);
    }
}
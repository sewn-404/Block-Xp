package net.sewn404.blockxp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.loader.api.FabricLoader; // NEW: To get the config directory
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class Blockxp implements ModInitializer {
	public static final String MOD_ID = "blockxp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Random object instance (no change here)
	private static final Random RANDOM = new Random();

	@Override
	public void onInitialize() {
		// NEW: Initialize the ConfigManager and load/create the config file
		ConfigManager.initialize(FabricLoader.getInstance().getConfigDir());

		LOGGER.info("Initializing {} - XP chances and amount loaded from config!", MOD_ID);
		LOGGER.info("XP Per Block: {}", ConfigManager.config.xpPerBlock);
		LOGGER.info("Wood Pickaxe Chance: {}%", ConfigManager.config.xpChanceWoodPickaxe);
		// You can log all chances here for verification

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (world instanceof ServerWorld) {
				String blockId = Registries.BLOCK.getId(state.getBlock()).toString();

				if (blockId.startsWith("minecraft:")) {
					ItemStack mainHandStack = player.getMainHandStack();

					if (mainHandStack.getItem() instanceof PickaxeItem) {
						MiningToolItem tool = (MiningToolItem) mainHandStack.getItem();
						ToolMaterial material = tool.getMaterial();

						int currentChance = ConfigManager.config.xpChanceDefaultPickaxe; // Use default from config

						// Use values from the loaded config
						if (material == ToolMaterials.WOOD) {
							currentChance = ConfigManager.config.xpChanceWoodPickaxe;
						} else if (material == ToolMaterials.STONE) {
							currentChance = ConfigManager.config.xpChanceStonePickaxe;
						} else if (material == ToolMaterials.IRON) {
							currentChance = ConfigManager.config.xpChanceIronPickaxe;
						} else if (material == ToolMaterials.GOLD) {
							currentChance = ConfigManager.config.xpChanceGoldPickaxe;
						} else if (material == ToolMaterials.DIAMOND) {
							currentChance = ConfigManager.config.xpChanceDiamondPickaxe;
						} else if (material == ToolMaterials.NETHERITE) {
							currentChance = ConfigManager.config.xpChanceNetheritePickaxe;
						}

						if (RANDOM.nextInt(100) < currentChance) {
							// Use XP_PER_BLOCK from the loaded config
							player.addExperience(ConfigManager.config.xpPerBlock);

							// Optional: Debugging log with config values
							// LOGGER.debug("Player {} broke vanilla block {} with {} pickaxe and gained {} XP ({}% chance).",
							//             player.getName().getString(), blockId, material.toString(), ConfigManager.config.xpPerBlock, currentChance);
						}
					}
				}
			}
		});

		LOGGER.info("{} initialized successfully with configurable options!", MOD_ID);
	}
}
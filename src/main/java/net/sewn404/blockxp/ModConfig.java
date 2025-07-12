package net.sewn404.blockxp;

// Gson annotations for transient fields and pretty printing
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModConfig {

    // These fields will be exposed to the JSON file
    @Expose // Ensures this field is included in JSON (useful if you use @Exclude for others)
    @SerializedName("xp_per_block") // Name in JSON file
    public int xpPerBlock = 1; // Default value

    @Expose
    @SerializedName("xp_chance_wood_pickaxe")
    public int xpChanceWoodPickaxe = 20;

    @Expose
    @SerializedName("xp_chance_stone_pickaxe")
    public int xpChanceStonePickaxe = 30;

    @Expose
    @SerializedName("xp_chance_iron_pickaxe")
    public int xpChanceIronPickaxe = 40;

    @Expose
    @SerializedName("xp_chance_gold_pickaxe")
    public int xpChanceGoldPickaxe = 60;

    @Expose
    @SerializedName("xp_chance_diamond_pickaxe")
    public int xpChanceDiamondPickaxe = 50;

    @Expose
    @SerializedName("xp_chance_netherite_pickaxe")
    public int xpChanceNetheritePickaxe = 55;

    @Expose
    @SerializedName("xp_chance_default_pickaxe")
    public int xpChanceDefaultPickaxe = 10;

    // You can add more configuration options here later if needed
}
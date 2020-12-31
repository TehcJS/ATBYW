package net.azagwen.atbyw.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import static net.azagwen.atbyw.main.AtbywMain.newModInteractionID;

public class AtbywModInteractionBlockUtils {
    public static String[] BETTER_NETHER_WOOD_NAMES = {
            "stalagnate",
            "reeds",
            "willow",
            "wart",
            "rubeus",
            "mushroom",
            "mushroom_fir",
            "anchor_tree",
            "nether_sakura"
    };

    public static String[] BETTER_END_WOOD_NAMES = {
            "mossy_glowshroom",
            "pythadendron",
            "end_lotus",
            "lacugrove",
            "dragon_tree",
            "tenanea",
            "helix_tree",
            "umbrella_tree"
    };

    public static void registerModInteractionBlocks(boolean fireproof, ItemGroup group, String block_name, String[] variant_type, Block[] block) {
        Item.Settings normalSettings = new Item.Settings().group(group);
        Item.Settings fireproofSettings = new Item.Settings().group(group).fireproof();

        if (block.length == variant_type.length)
            for (int i = 0; i < block.length; i++) {
                Registry.register(Registry.BLOCK, newModInteractionID(variant_type[i] + "_" + block_name), block[i]);
                Registry.register(Registry.ITEM, newModInteractionID(variant_type[i] + "_" + block_name), new BlockItem(block[i], (fireproof ? fireproofSettings : normalSettings)));
            }
        else
            throw new IllegalArgumentException("could not register " + block_name + " : mismatched lengths !");
    }
}
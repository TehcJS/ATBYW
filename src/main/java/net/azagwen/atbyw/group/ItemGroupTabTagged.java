package net.azagwen.atbyw.group;

import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

// This code was taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/itemgroup/ItemTab.java,
// which is licensed under MIT.
public class ItemGroupTabTagged {

    private final Tag<Item> itemTag;
    private final ItemStack icon;
    private final String name;

    public ItemGroupTabTagged(ItemStack icon, String name, Tag<Item> itemTag) {
        this.itemTag = itemTag;
        this.icon = icon;
        this.name = name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean matches(Item item) {
        return itemTag == null || itemTag.contains(item);
    }

    public boolean matches(ItemStack stack) {
        return matches(stack.getItem());
    }

    public Tag<Item> getItemTag() {
        return itemTag;
    }

    public String getTranslationKey() {
        return "itemGroup.subTab." + AtbywMain.nameSpace + "." + name;
    }
}
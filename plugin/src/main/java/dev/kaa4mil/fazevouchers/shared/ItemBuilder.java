package dev.kaa4mil.fazevouchers.shared;
import dev.kaa4mil.util.ColorTransformer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBuilder {

    @Getter private final ItemStack item;

    public ItemBuilder(@NotNull Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        if(name == null) return this;
        final ItemMeta itemMeta = item.getItemMeta();

        if(itemMeta != null) {
            itemMeta.setDisplayName(ColorTransformer.fix(name));
        }

        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if(lore == null) return this;
        final ItemMeta itemMeta = item.getItemMeta();

        if(itemMeta != null) {
            itemMeta.setLore(lore);
        }

        this.item.setItemMeta(itemMeta);
        return this;
    }

}
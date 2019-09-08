package com.stefthedev.villages.utilities.general;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Item {

    private String name;
    private Material material;
    private short durability;
    private String[] lore;
    private Map<Enchantment, Integer> enchantments;

    public Item name(Message message) {
        this.name = message.toString();
        return this;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public Item material(Material material) {
        this.material = material;
        return this;
    }

    public Item durability(int durability) {
        this.durability = (short) durability;
        return this;
    }

    public Item lore(Message message) {
        this.lore = message.toList().toArray(new String[0]);
        return this;
    }

    public Item lore(List<String> lore) {
        this.lore = lore.toArray(new String[0]);
        return this;
    }

    public Item enchants(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStack buildPlayer(OfflinePlayer offlinePlayer) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        if(itemMeta == null) return null;
        if(offlinePlayer != null) itemMeta.setOwningPlayer(offlinePlayer);
        if(name != null) itemMeta.setDisplayName(Chat.color(name));
        if (enchantments != null) enchantments.forEach(itemStack::addEnchantment);
        if(lore != null) itemMeta.setLore(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack build() {
        if(material == null) return null;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return null;
        if(durability >= 0) itemStack.setDurability(durability);
        if(name != null) itemMeta.setDisplayName(Chat.color(name));
        if (enchantments != null) enchantments.forEach(itemStack::addEnchantment);
        if(lore != null) itemMeta.setLore(Arrays.stream(lore).map(Chat::color).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
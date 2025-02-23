package com.teampotato.riftwind.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SacredRiftwind extends Enchantment {
    public SacredRiftwind() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public int getMaxLevel() {
        return 5;
    }
}

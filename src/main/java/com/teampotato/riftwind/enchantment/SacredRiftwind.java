package com.teampotato.riftwind.enchantment;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class SacredRiftwind extends Enchantment {
    public SacredRiftwind() {
        super(Rarity.UNCOMMON, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    public int getMaxLevel() {
        return 5;
    }
}

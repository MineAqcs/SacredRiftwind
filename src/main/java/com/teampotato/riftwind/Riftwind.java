package com.teampotato.riftwind;

import com.teampotato.riftwind.enchantment.SacredRiftwind;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Riftwind.MODID)
public class Riftwind {
    public static final String MODID = "riftwind";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);
    public static final RegistryObject<Enchantment> RIFTWIND = REGISTER.register("sacred_riftwind", SacredRiftwind::new);

    public Riftwind() {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Hello from Riftwind!");
        MinecraftForge.EVENT_BUS.addListener((LivingHurtEvent event) -> {
            LivingEntity entity = event.getEntityLiving();
            if (entity.level.isClientSide()) return;
            if (event.getSource().getEntity() instanceof LivingEntity) {
                LivingEntity source = (LivingEntity) event.getSource().getEntity();
                int level = Math.max(getLevel(source.getMainHandItem()), getLevel(source.getOffhandItem()));
                if (level != 0) {
                    float healthPercent = 1.0F - (source.getHealth() / source.getMaxHealth());
                    float bonus = (float) (level * 0.1D * healthPercent);
                    event.setAmount(event.getAmount() * (1.0F + bonus));
                    LOGGER.warn("Adding {}", bonus);
                }
            }
        });
    }

    private int getLevel(ItemStack itemStack) {
        if (itemStack.getTag() == null) return 0;
        return EnchantmentHelper.getItemEnchantmentLevel(RIFTWIND.get(), itemStack);
    }
}
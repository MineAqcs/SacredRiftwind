package com.teampotato.riftwind;

import com.teampotato.riftwind.enchantment.SacredRiftwind;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(Riftwind.MODID)
public class Riftwind {
    public static final String MODID = "riftwind";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);
    public static final RegistryObject<Enchantment> RIFTWIND = REGISTER.register("sacred_riftwind", SacredRiftwind::new);

    public Riftwind(@NotNull FMLJavaModLoadingContext context) {
        REGISTER.register(context.getModEventBus());
        LOGGER.info("Hello from Riftwind!");
        MinecraftForge.EVENT_BUS.addListener((LivingHurtEvent event) -> {
            LivingEntity entity = event.getEntity();
            if (entity.level().isClientSide()) return;
            if (event.getSource().getEntity() instanceof LivingEntity source) {
                int level = Math.max(getLevel(source.getMainHandItem()), getLevel(source.getOffhandItem()));
                if (level != 0) {
                    float healthPercent = 1.0F - (source.getHealth() / source.getMaxHealth());
                    float bonus = (float) (level * 0.1D * healthPercent);
                    event.setAmount(event.getAmount() * (1.0F + bonus));
                }
            }
        });
    }

    private int getLevel(@NotNull ItemStack itemStack) {
        if (itemStack.getTag() == null) return 0;
        return itemStack.getEnchantmentLevel(RIFTWIND.get());
    }
}
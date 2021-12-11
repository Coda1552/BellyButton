package teamdraco.dusted.init;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.dusted.BellyButton;
import teamdraco.dusted.common.enchantments.LintRollerEnchantment;

public class BellyButtonEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BellyButton.MOD_ID);

    public static final RegistryObject<Enchantment> LINT_ROLLER = REGISTER.register("lint_roller", () -> new LintRollerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
}
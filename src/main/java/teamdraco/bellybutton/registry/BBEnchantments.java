package teamdraco.bellybutton.registry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.common.enchantments.LintRollerEnchantment;

public class BBEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BellyButton.MOD_ID);

    public static final RegistryObject<Enchantment> LINT_ROLLER = REGISTER.register("lint_roller", () -> new LintRollerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
}
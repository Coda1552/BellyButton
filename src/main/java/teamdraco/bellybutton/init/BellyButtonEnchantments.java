package teamdraco.bellybutton.init;

import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.enchantments.LintRollerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BellyButton.MOD_ID);

    public static final RegistryObject<Enchantment> LINT_ROLLER = REGISTER.register("lint_roller", () -> new LintRollerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentType.WEAPON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
}
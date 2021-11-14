package teamdraco.bellybutton.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class LintRollerEnchantment extends Enchantment {
    public LintRollerEnchantment(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots) {
        super(rarity, type, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        return !ench.equals(Enchantments.MOB_LOOTING);
    }
}

package teamdraco.bellybutton.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.init.BellyButtonItems;

import net.minecraft.item.Item.Properties;

public class LintSweaterItem extends ArmorItem {
    public static final IArmorMaterial MATERIAL = new BellyButtonArmorMaterial(BellyButton.MOD_ID + ":lint", 3, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(BellyButtonItems.LINT.get()));

    public LintSweaterItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().tab(BellyButton.GROUP));
    }
}

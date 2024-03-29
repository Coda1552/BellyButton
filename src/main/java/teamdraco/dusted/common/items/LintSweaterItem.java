package teamdraco.dusted.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import teamdraco.dusted.BellyButton;
import teamdraco.dusted.init.BellyButtonItems;

public class LintSweaterItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new BellyButtonArmorMaterial(BellyButton.MOD_ID + ":lint", 3, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(BellyButtonItems.LINT.get()));

    public LintSweaterItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
        super(MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(BellyButton.GROUP));
    }
}

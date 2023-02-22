package teamdraco.bellybutton.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.registry.BBItems;

public class ItchySweaterItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new BellyButtonArmorMaterial(BellyButton.MOD_ID + ":dust", 3, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(BBItems.LINT.get()));

    public ItchySweaterItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
        super(MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(BellyButton.GROUP));
    }
}

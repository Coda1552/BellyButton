package teamdraco.dusted.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import teamdraco.dusted.Dusted;
import teamdraco.dusted.init.DustedItems;

public class MaidArmorItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new DustedArmorMaterial(Dusted.MOD_ID + ":maid", 2, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(DustedItems.DUST.get()));

    public MaidArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builderIn) {
        super(MATERIAL, slot, new Item.Properties().tab(Dusted.GROUP));
    }
}

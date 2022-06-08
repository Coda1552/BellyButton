package teamdraco.bellybutton.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.init.BBEnchantments;
import teamdraco.bellybutton.init.BBEntities;
import teamdraco.bellybutton.init.BBItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void dustBunnyAndMaidSpawn(StructureSpawnListGatherEvent event) {
        if (event.getStructure() == StructureFeature.WOODLAND_MANSION) {
            event.addEntitySpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(BBEntities.DUST_BUNNY.get(), 1, 1, 2));
            event.addEntitySpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BBEntities.MAID.get(), 1, 1, 1));
        }
        if (event.getStructure() == StructureFeature.MINESHAFT) {
            event.addEntitySpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(BBEntities.DUST_BUNNY.get(), 1, 1, 1));
        }
    }

    @SubscribeEvent
    public static void lintRollerDrops(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) attacker;
            ItemStack heldItem = livingEntity.getMainHandItem();
            if (EnchantmentHelper.getEnchantments(heldItem).containsKey(BBEnchantments.LINT_ROLLER.get())) {
                if (livingEntity.getRandom().nextDouble() < 0.035) {
                    event.getEntityLiving().spawnAtLocation(BBItems.LINT.get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> list = event.getGenericTrades();

        list.add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(BBItems.VACUUM.get(), 1), 3, 5, 1.5f));
    }
}

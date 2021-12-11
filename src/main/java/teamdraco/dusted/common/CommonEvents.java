package teamdraco.dusted.common;

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
import teamdraco.dusted.Dusted;
import teamdraco.dusted.init.DustedEnchantments;
import teamdraco.dusted.init.DustedEntities;
import teamdraco.dusted.init.DustedItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = Dusted.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void dustBunnyAndMaidSpawn(StructureSpawnListGatherEvent event) {
        if (event.getStructure() == StructureFeature.WOODLAND_MANSION) {
            event.addEntitySpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(DustedEntities.DUST_BUNNY.get(), 1, 1, 2));
            event.addEntitySpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(DustedEntities.MAID.get(), 1, 1, 1));
        }
        if (event.getStructure() == StructureFeature.MINESHAFT) {
            event.addEntitySpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(DustedEntities.DUST_BUNNY.get(), 1, 1, 1));
        }
    }

    @SubscribeEvent
    public static void lintRollerDrops(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) attacker;
            ItemStack heldItem = livingEntity.getMainHandItem();
            if (EnchantmentHelper.getEnchantments(heldItem).containsKey(DustedEnchantments.LINT_ROLLER.get())) {
                if (livingEntity.getRandom().nextDouble() < 0.035) {
                    event.getEntityLiving().spawnAtLocation(DustedItems.DUST.get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> list = event.getGenericTrades();

        list.add(new BasicItemListing(new ItemStack(Items.EMERALD, 3), new ItemStack(DustedItems.VACUUM.get(), 1), 3, 5, 1.5f));
    }
}

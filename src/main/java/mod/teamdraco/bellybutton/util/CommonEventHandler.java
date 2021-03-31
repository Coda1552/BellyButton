package mod.teamdraco.bellybutton.util;

import mod.teamdraco.bellybutton.BellyButton;
import mod.teamdraco.bellybutton.entity.DustBunnyEntity;
import mod.teamdraco.bellybutton.init.BellyButtonEnchantments;
import mod.teamdraco.bellybutton.init.BellyButtonEntities;
import mod.teamdraco.bellybutton.init.BellyButtonItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = BellyButton.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {

    @SubscribeEvent
    public static void dustBunnySpawn(StructureSpawnListGatherEvent event) {
        if (event.getStructure() == Structure.WOODLAND_MANSION || event.getStructure() == Structure.MINESHAFT) {
            event.addEntitySpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(BellyButtonEntities.DUST_BUNNY.get(), 2, 1, 1));
        }
    }

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.DESERT) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(BellyButtonEntities.DUST_BUNNY.get(), 4, 1, 1));
        }
    }*/

    @SubscribeEvent
    public static void lintRollerDrops(LivingDeathEvent event) {
        Entity attacker = event.getSource().getTrueSource();

        if(attacker instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) attacker;
            ItemStack heldItem = livingEntity.getHeldItemMainhand();
            if (EnchantmentHelper.getEnchantments(heldItem).containsKey(BellyButtonEnchantments.LINT_ROLLER.get())) {
                if (livingEntity.getRNG().nextDouble() < 0.035) {
                    event.getEntityLiving().entityDropItem(BellyButtonItems.LINT.get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent event) {
        if (!event.getName().toString().equals("minecraft:entities/villager")) {
            return;
        }
        String lootLocation = event.getName().toString().replace("minecraft:entities/villager", "");

        event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(BellyButton.MOD_ID, "inject/villager")).weight(1)).bonusRolls(0, 0).name("inject").build());
    }

    //Thanks to Wolf for helping with the trade code
    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> list = event.getGenericTrades();

        list.add(new ItemsForItemsTrade(new ItemStack(Items.EMERALD, 3), new ItemStack(BellyButtonItems.VACUUM.get(), 1), 3, 5, 1.5f));
    }

    private static VillagerTrades.ITrade cdForItems(ItemStack selling, int maxUses, int xp) {
        return new ItemsForItemsTrade(new ItemStack(Items.EMERALD, 5), selling, maxUses, xp, 0);
    }

    private static VillagerTrades.ITrade cdForItems(Item item, int count, int maxUses, int xp) {
        return cdForItems(new ItemStack(item, count), maxUses, xp);
    }

    private static class ItemsForItemsTrade implements VillagerTrades.ITrade {
        private final ItemStack buying1, buying2, selling;
        private final int maxUses, xp;
        private final float priceMultiplier;

        public ItemsForItemsTrade(ItemStack buying1, ItemStack buying2, ItemStack selling, int maxUses, int xp, float priceMultiplier) {
            this.buying1 = buying1;
            this.buying2 = buying2;
            this.selling = selling;
            this.maxUses = maxUses;
            this.xp = xp;
            this.priceMultiplier = priceMultiplier;
        }

        public ItemsForItemsTrade(ItemStack buying1, ItemStack selling, int maxUses, int xp, float priceMultiplier) {
            this(buying1, ItemStack.EMPTY, selling, maxUses, xp, priceMultiplier);
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(buying1, buying2, selling, maxUses, xp, priceMultiplier);
        }
    }
}

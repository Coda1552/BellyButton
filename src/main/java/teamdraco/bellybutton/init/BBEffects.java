package teamdraco.bellybutton.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.common.effects.ItchyEffect;

public class BBEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BellyButton.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, BellyButton.MOD_ID);

    public static final RegistryObject<MobEffect> ITCHY = EFFECTS.register("itchy", () -> new ItchyEffect(MobEffectCategory.HARMFUL, 0x98928a));

    public static final RegistryObject<Potion> ITCHY_NORMAL = POTIONS.register("itchy", () -> new Potion(new MobEffectInstance(ITCHY.get(), 600)));
    public static final RegistryObject<Potion> ITCHY_STRONG = POTIONS.register("itchy_strong", () -> new Potion(new MobEffectInstance(ITCHY.get(), 600, 1)));
    public static final RegistryObject<Potion> ITCHY_LONG = POTIONS.register("itchy_long", () -> new Potion(new MobEffectInstance(ITCHY.get(), 900)));

    public static void brewingRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(BBItems.LINT.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), ITCHY_NORMAL.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), ITCHY_NORMAL.get())), Ingredient.of(Items.GLOWSTONE_DUST), PotionUtils.setPotion(new ItemStack(Items.POTION), ITCHY_STRONG.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), ITCHY_NORMAL.get())), Ingredient.of(Items.REDSTONE), PotionUtils.setPotion(new ItemStack(Items.POTION), ITCHY_LONG.get()));
    }
}
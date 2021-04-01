package teamdraco.bellybutton.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.effects.ItchyEffect;

public class BellyButtonEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BellyButton.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, BellyButton.MOD_ID);

    public static final RegistryObject<Effect> ITCHY = EFFECTS.register("itchy", () -> new ItchyEffect(EffectType.HARMFUL, 0x98928a));

    public static final RegistryObject<Potion> ITCHY_NORMAL = POTIONS.register("itchy", () -> new Potion(new EffectInstance(ITCHY.get(), 600)));
    public static final RegistryObject<Potion> ITCHY_STRONG = POTIONS.register("itchy_strong", () -> new Potion(new EffectInstance(ITCHY.get(), 600, 1)));
    public static final RegistryObject<Potion> ITCHY_LONG = POTIONS.register("itchy_long", () -> new Potion(new EffectInstance(ITCHY.get(), 900)));

    public static void brewingRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.fromItems(BellyButtonItems.LINT.get()), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ITCHY_NORMAL.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ITCHY_NORMAL.get())), Ingredient.fromItems(Items.GLOWSTONE_DUST), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ITCHY_STRONG.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ITCHY_NORMAL.get())), Ingredient.fromItems(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ITCHY_LONG.get()));
    }
}
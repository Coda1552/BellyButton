package mod.teamdraco.bellybutton.init;

import mod.teamdraco.bellybutton.BellyButton;
import mod.teamdraco.bellybutton.entity.DustBunnyEntity;
import mod.teamdraco.bellybutton.entity.MaidEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, BellyButton.MOD_ID);

    public static final RegistryObject<EntityType<DustBunnyEntity>> DUST_BUNNY = REGISTER.register("dust_bunny", () -> EntityType.Builder.<DustBunnyEntity>create(DustBunnyEntity::new, EntityClassification.CREATURE).size(1.0f, 1.0f).build(new ResourceLocation(BellyButton.MOD_ID, "dust_bunny").toString()));
    public static final RegistryObject<EntityType<MaidEntity>> MAID = REGISTER.register("maid", () -> EntityType.Builder.<MaidEntity>create(MaidEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).trackingRange(8).build(new ResourceLocation(BellyButton.MOD_ID, "maid").toString()));
}
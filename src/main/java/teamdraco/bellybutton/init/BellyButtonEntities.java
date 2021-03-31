package teamdraco.bellybutton.init;

import net.minecraft.entity.Entity;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.entity.DustBunnyEntity;
import teamdraco.bellybutton.entity.EvilDustBunnyEntity;
import teamdraco.bellybutton.entity.MaidEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, BellyButton.MOD_ID);

    public static final RegistryObject<EntityType<DustBunnyEntity>> DUST_BUNNY = create("dust_bunny", EntityType.Builder.create(DustBunnyEntity::new, EntityClassification.CREATURE).size(1.0f, 1.0f));
    public static final RegistryObject<EntityType<MaidEntity>> MAID = create("maid", EntityType.Builder.create(MaidEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).trackingRange(8));
    public static final RegistryObject<EntityType<EvilDustBunnyEntity>> EVIL_DUST_BUNNY = create("evil_dust_bunny", EntityType.Builder.create(EvilDustBunnyEntity::new, EntityClassification.MONSTER).size(1.0f, 1.0f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(BellyButton.MOD_ID + "." + name));
    }
}
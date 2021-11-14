package teamdraco.bellybutton.init;

import net.minecraft.entity.Entity;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.entity.DustBunnyEntity;
import teamdraco.bellybutton.entity.EvilDustBunnyEntity;
import teamdraco.bellybutton.entity.MaidEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, BellyButton.MOD_ID);

    public static final RegistryObject<EntityType<DustBunnyEntity>> DUST_BUNNY = create("dust_bunny", EntityType.Builder.of(DustBunnyEntity::new, EntityClassification.CREATURE).sized(1.0f, 1.0f));
    public static final RegistryObject<EntityType<MaidEntity>> MAID = create("maid", EntityType.Builder.of(MaidEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<EvilDustBunnyEntity>> EVIL_DUST_BUNNY = create("evil_dust_bunny", EntityType.Builder.of(EvilDustBunnyEntity::new, EntityClassification.MONSTER).sized(1.0f, 1.0f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(BellyButton.MOD_ID + "." + name));
    }
}
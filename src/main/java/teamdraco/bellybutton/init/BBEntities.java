package teamdraco.bellybutton.init;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;
import teamdraco.bellybutton.common.entities.DustBunnyEntity;
import teamdraco.bellybutton.common.entities.EvilDustBunnyEntity;
import teamdraco.bellybutton.common.entities.MaidEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BBEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, BellyButton.MOD_ID);

    public static final RegistryObject<EntityType<DustBunnyEntity>> DUST_BUNNY = create("dust_bunny", EntityType.Builder.of(DustBunnyEntity::new, MobCategory.CREATURE).sized(1.0f, 1.0f));
    public static final RegistryObject<EntityType<MaidEntity>> MAID = create("maid", EntityType.Builder.of(MaidEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<EvilDustBunnyEntity>> EVIL_DUST_BUNNY = create("evil_dust_bunny", EntityType.Builder.of(EvilDustBunnyEntity::new, MobCategory.MONSTER).sized(1.0f, 1.0f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(BellyButton.MOD_ID + "." + name));
    }
}
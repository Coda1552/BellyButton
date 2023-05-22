package teamdraco.bellybutton.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.bellybutton.BellyButton;

public class BBPoiTypes {
    public static final DeferredRegister<PoiType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, BellyButton.MOD_ID);

    public static final RegistryObject<PoiType> NAVEL_CAVITY = POIS.register("poi_navel_cavity", () ->
            new PoiType("poi_navel_cavity", ImmutableSet.of(), 1, 1));

}

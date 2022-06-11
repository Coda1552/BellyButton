package teamdraco.bellybutton.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import teamdraco.bellybutton.BellyButton;

public class BBDimension {
    public static final ResourceKey<DimensionType> THE_NAVEL_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(BellyButton.MOD_ID, "the_navel"));
    public static final ResourceKey<Level> THE_NAVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BellyButton.MOD_ID, "the_navel"));

}

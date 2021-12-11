package teamdraco.dusted.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.dusted.Dusted;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DustedSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Dusted.MOD_ID);

    public static final RegistryObject<SoundEvent> VACUUM = REGISTER.register("vacuum", () -> new SoundEvent(new ResourceLocation(Dusted.MOD_ID, "vacuum")));
    public static final RegistryObject<SoundEvent> BELLY_BOPPIN = REGISTER.register("belly_boppin", () -> new SoundEvent(new ResourceLocation(Dusted.MOD_ID, "belly_boppin")));
}

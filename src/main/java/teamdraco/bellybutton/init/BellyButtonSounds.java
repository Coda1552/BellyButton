package teamdraco.bellybutton.init;

import teamdraco.bellybutton.BellyButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BellyButtonSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BellyButton.MOD_ID);

    public static final RegistryObject<SoundEvent> VACUUM = REGISTER.register("vacuum", () -> new SoundEvent(new ResourceLocation(BellyButton.MOD_ID, "vacuum")));
    public static final RegistryObject<SoundEvent> BELLY_BOPPIN = REGISTER.register("belly_boppin", () -> new SoundEvent(new ResourceLocation(BellyButton.MOD_ID, "belly_boppin")));
}

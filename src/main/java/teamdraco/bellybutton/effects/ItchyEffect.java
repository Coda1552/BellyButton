package teamdraco.bellybutton.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class ItchyEffect extends Effect {
    public ItchyEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getHealth() > 1.0F && entityLivingBaseIn.getRNG().nextFloat() < 0.5D) {
            entityLivingBaseIn.attackEntityFrom(DamageSource.GENERIC, 2.0F);
        }
    }

    public boolean isReady(int duration, int amplifier) {
        int k = 50 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
}

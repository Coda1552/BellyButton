package teamdraco.bellybutton.common.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ItchyEffect extends MobEffect {
    public ItchyEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        float f = entity.getArmorCoverPercentage();

        if (entity.getHealth() > 1.0F && entity.getRandom().nextBoolean()) {
            entity.hurt(DamageSource.GENERIC, f * 2.0F);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 50 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
}

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
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        float f = entity.getArmorCoverPercentage();

        if (entity.getHealth() > 1.0F && entity.getRandom().nextFloat() < 0.5D) {
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

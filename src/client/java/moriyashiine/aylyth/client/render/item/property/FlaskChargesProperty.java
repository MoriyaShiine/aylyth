package moriyashiine.aylyth.client.render.item.property;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record FlaskChargesProperty() implements NumericProperty {
    public static final MapCodec<FlaskChargesProperty> CODEC = MapCodec.unit(new FlaskChargesProperty());

    @Override
    public float getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity holder, int seed) {
        Integer uses = stack.get(AylythDataComponentTypes.FLASK_CHARGES);
        if (uses != null) {
            return uses;
        }
        return 0;
    }

    @Override
    public MapCodec<? extends NumericProperty> getCodec() {
        return CODEC;
    }
}

package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.ai.sensor.HindAttackablesSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.NearbyPledgedPlayerSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.OwningPlayerSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.ScionSpecificSensor;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public interface AylythSensorTypes {

    SensorType<ScionSpecificSensor> SCION_SPECIFIC = register("scion_specific", ScionSpecificSensor::new);
    SensorType<OwningPlayerSensor<TulpaEntity>> TULPA_SPECIFIC = register("tulpa_specific", OwningPlayerSensor::new);
    SensorType<NearbyPledgedPlayerSensor<WreathedHindEntity>> NEARBY_PLEDGED_PLAYER = register("nearby_pledged_player", NearbyPledgedPlayerSensor::new);
    SensorType<HindAttackablesSensor> HIND_ATTACKABLES = register("hind_attackables", HindAttackablesSensor::new);

    private static <S extends Sensor<?>> SensorType<S> register(String name, Supplier<S> factory) {
        return Registry.register(Registries.SENSOR_TYPE, AylythUtil.id(name), new SensorType<>(factory));
    }

    // Load static initializer
    static void register() {}
}

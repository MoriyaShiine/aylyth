package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.ai.sensor.HindSpecificSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.ScionSpecificSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.OwningPlayerSensor;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.function.Supplier;

public class ModSensorTypes {
    public static final SensorType<ScionSpecificSensor> SCION_SPECIFIC_SENSOR = register("scion_specific", ScionSpecificSensor::new);
    public static final SensorType<OwningPlayerSensor<TulpaEntity>> TULPA_SPECIFIC_SENSOR = register("tulpa_specific", OwningPlayerSensor::new);
    public static final SensorType<HindSpecificSensor> HIND_SPECIFIC_SENSOR = register("hind_specific", HindSpecificSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, new Identifier(id), new SensorType<>(factory));
    }


    public static void init() {

    }
}

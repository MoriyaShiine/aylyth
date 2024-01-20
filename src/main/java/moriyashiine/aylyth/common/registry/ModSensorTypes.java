package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.ai.sensor.HindAttackablesSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.NearbyPledgedPlayerSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.ScionSpecificSensor;
import moriyashiine.aylyth.common.entity.ai.sensor.OwningPlayerSensor;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModSensorTypes {
    public static final SensorType<ScionSpecificSensor> SCION_SPECIFIC = register("scion_specific", ScionSpecificSensor::new);
    public static final SensorType<OwningPlayerSensor<TulpaEntity>> TULPA_SPECIFIC = register("tulpa_specific", OwningPlayerSensor::new);
    public static final SensorType<NearbyPledgedPlayerSensor<WreathedHindEntity>> NEARBY_PLEDGED_PLAYER = register("nearby_pledged_player", NearbyPledgedPlayerSensor::new);
    public static final SensorType<HindAttackablesSensor> HIND_ATTACKABLES = register("hind_attackables", HindAttackablesSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registry.SENSOR_TYPE, new Identifier(id), new SensorType<>(factory));
    }


    public static void init() {

    }
}

package moriyashiine.aylyth.api;

import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;

public interface AylythEntityApi {
    EntityApiLookup<VitalHealthHolder, Void> VITAL_HOLDER = EntityApiLookup.get(Aylyth.id("vital_holder"), VitalHealthHolder.class, Void.class);
}

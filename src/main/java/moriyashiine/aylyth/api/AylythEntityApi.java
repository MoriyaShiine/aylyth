package moriyashiine.aylyth.api;

import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;

public interface AylythEntityApi {
    EntityApiLookup<VitalHealthHolder, Void> VITAL_HOLDER = EntityApiLookup.get(AylythUtil.id("vital_holder"), VitalHealthHolder.class, Void.class);
}

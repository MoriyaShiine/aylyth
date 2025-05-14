package moriyashiine.aylyth.common.entity;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import moriyashiine.aylyth.common.entity.attachments.CuirassStages;
import moriyashiine.aylyth.common.entity.attachments.VitalHealth;
import moriyashiine.aylyth.common.entity.attachments.YmpeThorns;
import moriyashiine.aylyth.common.entity.attachments.RiderControls;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Unit;

@SuppressWarnings("UnstableApiUsage")
public interface AylythEntityAttachmentTypes {

    AttachmentType<Unit> PREVENT_DROPS = AttachmentRegistry.create(Aylyth.id("prevent_drops"),
            builder -> builder.persistent(Codec.unit(Unit.INSTANCE))
                              .initializer(() -> Unit.INSTANCE)
    );

    AttachmentType<YmpeInfestation> YMPE_INFESTATION = AttachmentRegistry.create(Aylyth.id("ympe_infestation"),
            builder -> builder.persistent(YmpeInfestation.CODEC)
                    .initializer(YmpeInfestation::new)
                    .syncWith(YmpeInfestation.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    AttachmentType<CuirassStages> CUIRASS = AttachmentRegistry.create(Aylyth.id("cuirass"),
            builder -> builder.persistent(CuirassStages.CODEC)
                    .initializer(CuirassStages::new)
                    .syncWith(CuirassStages.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    AttachmentType<VitalHealth> VITAL_HEALTH = AttachmentRegistry.create(Aylyth.id("vital_health"),
            builder -> builder.persistent(VitalHealth.CODEC)
                    .initializer(VitalHealth::new)
                    .syncWith(VitalHealth.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    AttachmentType<YmpeThorns> YMPE_THORNS = AttachmentRegistry.create(Aylyth.id("ympe_thorns"),
            builder -> builder.persistent(YmpeThorns.CODEC)
                    .initializer(YmpeThorns::new)
                    .syncWith(YmpeThorns.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    // TODO: Revise and get rid of
    AttachmentType<RiderControls> RIDER = AttachmentRegistry.create(Aylyth.id("rider"),
            builder -> builder.initializer(RiderControls::new)
                    .syncWith(RiderControls.PACKET_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    // Load static initializer
    static void register() {}
}
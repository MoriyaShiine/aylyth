package moriyashiine.aylyth.common.entity;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Unit;

@SuppressWarnings("UnstableApiUsage")
public interface AylythEntityAttachmentTypes {

    AttachmentType<Unit> PREVENT_DROPS = AttachmentRegistry.<Unit>builder()
            .persistent(Codec.unit(Unit.INSTANCE))
            .initializer(() -> Unit.INSTANCE)
            .buildAndRegister(Aylyth.id("prevent_drops"));

    // Load static initializer
    static void register() {}
}
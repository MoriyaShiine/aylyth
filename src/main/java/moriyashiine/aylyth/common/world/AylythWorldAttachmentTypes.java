package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.attachments.PledgeState;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

@SuppressWarnings("UnstableApiUsage")
public interface AylythWorldAttachmentTypes {

    AttachmentType<PledgeState> PLEDGE_STATE = AttachmentRegistry.<PledgeState>builder()
            .persistent(PledgeState.CODEC)
            .initializer(PledgeState::new)
            .buildAndRegister(AylythUtil.id("pledge_state"));

    // Load static initializer
    static void register() {}
}

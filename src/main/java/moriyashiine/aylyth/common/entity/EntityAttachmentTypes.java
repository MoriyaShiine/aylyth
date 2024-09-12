package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.attachment.PledgeState;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public interface EntityAttachmentTypes {

    AttachmentType<PledgeState> PLEDGE_STATE = AttachmentRegistry.<PledgeState>builder()
            .persistent(PledgeState.CODEC)
            .initializer(PledgeState::new)
            .buildAndRegister(AylythUtil.id("pledge_state"));

    // Load static initializer
    static void register() {}
}

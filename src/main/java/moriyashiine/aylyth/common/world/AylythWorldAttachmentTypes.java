package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.attachment.PledgeState;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public interface AylythWorldAttachmentTypes {

    AttachmentType<PledgeState> PLEDGE_STATE = AttachmentRegistry.<PledgeState>builder()
            .persistent(PledgeState.CODEC)
            .initializer(PledgeState::new)
            .buildAndRegister(AylythUtil.id("pledge_state"));

    // Load static initializer
    static void register() {}
}

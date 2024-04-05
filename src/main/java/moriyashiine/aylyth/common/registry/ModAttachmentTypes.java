package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.attachment.PledgeState;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class ModAttachmentTypes {
    public static final AttachmentType<PledgeState> PLEDGE_STATE = AttachmentRegistry.<PledgeState>builder()
            .persistent(PledgeState.CODEC)
            .initializer(PledgeState::new)
            .buildAndRegister(AylythUtil.id("pledge_state"));

    public static void init() {}
}

package moriyashiine.aylyth.common.integration.bewitchment;

import moriyashiine.bewitchment.common.item.TaglockItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class BewitchmentCompat {
    private BewitchmentCompat() {}

    @Nullable
    public static UUID getTaglockOwner(ItemStack stack) {
        if (stack.getItem() instanceof TaglockItem) {
            return TaglockItem.getTaglockUUID(stack);
        }
        return null;
    }
}

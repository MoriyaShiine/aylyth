package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.Component;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HindPledgeComponent implements Component, HindPledgeHolder {
    private UUID hindUuid;

    public HindPledgeComponent() {
        this(null);
    }

    public HindPledgeComponent(UUID hindUuid) {
        this.hindUuid = hindUuid;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.containsUuid("pledged_hind")) {
            hindUuid = tag.getUuid("pledged_hind");
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        if (hindUuid != null) {
            tag.putUuid("pledged_hind", hindUuid);
        }
    }

    @Override
    public UUID getHindUuid() {
        return hindUuid;
    }

    @Override
    public void setHindUuid(@Nullable UUID uuid) {
        this.hindUuid = uuid;
    }
}

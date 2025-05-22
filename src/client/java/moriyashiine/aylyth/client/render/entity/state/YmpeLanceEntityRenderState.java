package moriyashiine.aylyth.client.render.entity.state;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public class YmpeLanceEntityRenderState extends EntityRenderState {
    public float pitch;
    public float yaw;
    public double yOffset;
    public ItemRenderState item = new ItemRenderState();
}

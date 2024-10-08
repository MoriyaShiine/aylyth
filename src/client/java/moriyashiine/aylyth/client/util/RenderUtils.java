package moriyashiine.aylyth.client.util;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;

public final class RenderUtils {
    private RenderUtils() {}

    public static void copyOver(RenderContext context, MatrixStack matrixStack) {
        context.pushTransform(quad -> {
            Vector3f vec = new Vector3f();
            for (int i = 0; i < 4; i++) {
                quad.copyPos(i, vec);
                vec.mulProject(matrixStack.peek().getPositionMatrix());
                quad.pos(i, vec);
            }
            return true;
        });
    }
}

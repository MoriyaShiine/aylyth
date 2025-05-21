package moriyashiine.aylyth.client.util;

import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;

public final class RenderUtils {
    private RenderUtils() {}

    public static void copyOver(QuadEmitter emitter, MatrixStack matrixStack) {
        emitter.pushTransform(quad -> {
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

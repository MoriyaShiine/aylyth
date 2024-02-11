package moriyashiine.aylyth.client.model.item;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.joml.*;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class BigItemModel implements BakedModel {
    private final BakedModel gui;
    private final BakedModel handheld;

    public BigItemModel(BakedModel gui, BakedModel handheld) {
        this.gui = gui;
        this.handheld = handheld;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        ModelTransformationMode mode = context.itemTransformationMode();
        if (mode.isFirstPerson() || mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND) {
            context.pushTransform(quad -> {
                Transformation transform = handheld.getTransformation().getTransformation(mode);
                MatrixStack matrixStack = new MatrixStack();
                transform.apply(MinecraftClient.getInstance().player.getMainHandStack() != stack, matrixStack);
                Matrix4f posMatrix = matrixStack.peek().getPositionMatrix();
                Vector3fc scale = posMatrix.getScale(new Vector3f());
                Quaternionfc rotation = posMatrix.getUnnormalizedRotation(new Quaternionf());
                Vector3fc translation = posMatrix.getTranslation(new Vector3f());

                Vector3f pos0 = new Vector3f(quad.x(0), quad.y(0), quad.z(0));
                Vector3f pos1 = new Vector3f(quad.x(1), quad.y(1), quad.z(1));
                Vector3f pos2 = new Vector3f(quad.x(2), quad.y(2), quad.z(2));
                Vector3f pos3 = new Vector3f(quad.x(3), quad.y(3), quad.z(3));

                pos0.add(translation).add(0.2f, -0.65f, -0.85f);
                pos1.add(translation).add(0.2f, -0.65f, -0.85f);
                pos2.add(translation).add(0.2f, -0.65f, -0.85f);
                pos3.add(translation).add(0.2f, -0.65f, -0.85f);

                rotation.transform(pos0);
                rotation.transform(pos1);
                rotation.transform(pos2);
                rotation.transform(pos3);

                pos0.mul(scale);
                pos1.mul(scale);
                pos2.mul(scale);
                pos3.mul(scale);

                quad.pos(0, pos0);
                quad.pos(1, pos1);
                quad.pos(2, pos2);
                quad.pos(3, pos3);

                return true;
            });
            handheld.emitItemQuads(stack, randomSupplier, context);
            context.popTransform();
        } else {
            gui.emitItemQuads(stack, randomSupplier, context);
        }
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return gui.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}

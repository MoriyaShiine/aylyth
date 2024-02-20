package moriyashiine.aylyth.client.render.block.entity;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.block.entity.VitalThuribleBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;

public class VitalThuribleBlockEntityRenderer implements BlockEntityRenderer<VitalThuribleBlockEntity> {
    private static Map<Integer, Pair<Pair<Float, Float>, Pair<Float, Integer>>> POS () {
        return Util.make(Maps.newHashMap(), map -> {//(x,z,y,rot)
            map.put(0, Pair.of(Pair.of(-0.1F,-0.1F), Pair.of(0.03F, 25)));
            map.put(1, Pair.of(Pair.of(0.1F,-0.1F), Pair.of(0.06F, -45)));
            map.put(2, Pair.of(Pair.of(0.1F,0.1F), Pair.of(0.01F, 15)));
            map.put(3, Pair.of(Pair.of(-0.1F,0.1F), Pair.of(0.07F, 65)));
            map.put(4, Pair.of(Pair.of(0.05F,0.05F), Pair.of(0.1F, -35)));
        });
    }

    public VitalThuribleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(VitalThuribleBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.translate(0.5, 0.25, 0.5);
        for(int i = 0; i < entity.getStack(0).getCount(); i++){
            matrices.push();
            renderStack(matrices, vertexConsumers, light, overlay, POS().get(i).getFirst().getFirst(), POS().get(i).getSecond().getFirst(), POS().get(i).getFirst().getSecond(), POS().get(i).getSecond().getSecond(), entity.getStack(0));
            matrices.pop();
        }
    }
    private void renderStack(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float x, float y, float z, int rot, ItemStack stack) {
        matrices.translate(x, y, z);
        matrices.scale(1 / 2.5f, 1 / 2.5f, 1 / 2.5f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rot));
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, null, 0);
    }
}

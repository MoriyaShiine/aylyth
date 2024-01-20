package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.render.RenderTypes;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SoulmouldEyesLayer extends GeoRenderLayer<SoulmouldEntity> {
    public SoulmouldEyesLayer(GeoRenderer<SoulmouldEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, SoulmouldEntity entitylivingbaseIn, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        Identifier location = new Identifier(Aylyth.MOD_ID, "textures/entity/living/mould/eyes.png");
        RenderLayer armor = RenderLayer.getEyes(location);
        this.getRenderer().reRender(getGeoModel().getBakedModel(getGeoModel().getModelResource(entitylivingbaseIn)),
                matrixStackIn,
                bufferIn,
                entitylivingbaseIn,
                armor,
                bufferIn.getBuffer(armor),
                partialTicks,
                -packedLightIn,
                OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, MathHelper.clamp(120.0F - (float)packedLightIn, 0.0F, 120.0F) / 160.0F);
    }
}
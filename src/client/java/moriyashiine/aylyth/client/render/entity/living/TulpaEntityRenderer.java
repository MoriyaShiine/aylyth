package moriyashiine.aylyth.client.render.entity.living;


import com.mojang.authlib.GameProfile;
import moriyashiine.aylyth.client.model.entity.TulpaEntityModel;
import moriyashiine.aylyth.client.render.block.entity.WoodyGrowthBlockEntityRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import moriyashiine.aylyth.mixin.client.PlayerSkinTextureAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class TulpaEntityRenderer extends GeoEntityRenderer<TulpaEntity> {
    private final TextureManager textureManager;
    private final ResourceManager resourceManager;
    private final AdditiveTexture defaultTexture;
    public static final Map<GameProfile, AdditiveTexture> TEXTURE_CACHE = new HashMap<>();
    private TulpaEntity.TulpaPlayerEntity tulpaPlayerEntity;
    protected TulpaEntityModel.TulpaPlayerEntityModel model;

    private static final String RIGHT_CLAWS = "right_claws";
    private static final String LEFT_CLAWS = "left_claws";

    public TulpaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TulpaEntityModel());
        this.model = new TulpaEntityModel.TulpaPlayerEntityModel();
        this.textureManager = MinecraftClient.getInstance().getTextureManager();
        this.resourceManager = MinecraftClient.getInstance().getResourceManager();
        this.defaultTexture = new AdditiveTexture(DefaultSkinHelper.getTexture(), false);
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Override
            protected ItemStack getStackForBone(GeoBone bone, TulpaEntity tulpa) {
                return switch (bone.getName()) {
                    case RIGHT_CLAWS -> tulpa.getMainHandStack();
                    case LEFT_CLAWS -> tulpa.getOffHandStack();
                    default -> null;
                };
            }

            @Override
            protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, TulpaEntity animatable) {
                return switch (bone.getName()) {
                    case RIGHT_CLAWS -> ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
                    case LEFT_CLAWS -> ModelTransformationMode.THIRD_PERSON_LEFT_HAND;
                    default -> super.getTransformTypeForStack(bone, stack, animatable);
                };
            }

            @Override
            protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, TulpaEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == animatable.getMainHandStack()) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80f));
                    poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-25f));
                    poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-10));
                    poseStack.translate(0, 0, -0.15);

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                }
                else if (stack == animatable.getOffHandStack()) {
                    poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10f));
                    poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20f));
                    poseStack.translate(0, -0.4, -0.35);

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, -0.9, 0.5);
                        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
        // TODO: add layer for rendering armor
    }

    @Override
    public void render(TulpaEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if (entity.getSkinUuid() == null || entity.getDataTracker().get(TulpaEntity.TRANSFORMING) || (entity.getHealth() < 0.01 || entity.isDead())) {
            super.render(entity, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
        } else {
            copyEntityStateAndRender(matrixStack, entity, entityYaw, partialTicks, bufferIn, packedLightIn);
        }
    }

    @Override
    public RenderLayer getRenderType(TulpaEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        if (animatable.getSkinProfile() != null) {
            return getFusedTexture(animatable.getSkinProfile()).renderLayer;
        } else if (animatable.getSkinUuid() != null) {
            GameProfile profile = new GameProfile(animatable.getSkinUuid(), "");
            animatable.setSkinProfile(profile);
            return getFusedTexture(animatable.getSkinProfile()).renderLayer;
        } else {
            return RenderLayer.getEntityTranslucent(getTexture(animatable));
        }
    }

    private AdditiveTexture getFusedTexture(GameProfile profile) {
        return profile == null ? defaultTexture : TEXTURE_CACHE.compute(profile, (gameProfile, additiveTexture) -> {
            if (additiveTexture == null) {
                Identifier playerTexture = WoodyGrowthBlockEntityRenderer.getPlayerTexture(profile.getId());
                if (playerTexture != DefaultSkinHelper.getTexture(profile.getId())) {
                    return new AdditiveTexture(playerTexture, true);
                } else {
                    return defaultTexture;
                }
            } else {
                if (additiveTexture.needsUpdate) {
                    additiveTexture.updateTexture();
                }
                return additiveTexture;
            }
        });
    }

    public VertexConsumerProvider rtb;
    public Identifier whTexture;

    public final class AdditiveTexture implements AutoCloseable {
        public static final Logger LOGGER = LogManager.getLogger(Aylyth.MOD_ID + ":texturegen");
        private static final Identifier TULPA_TEXTURE = Aylyth.id("textures/entity/living/tulpa/tulpa.png");
        private final Identifier base;
        private final boolean playerSkin;
        private final RenderLayer renderLayer;
        private final NativeImageBackedTexture texture;
        public boolean needsUpdate;

        AdditiveTexture(Identifier base, boolean playerSkin) {
            this.base = base;
            this.playerSkin = playerSkin;
            texture = new NativeImageBackedTexture(new NativeImage(128, 128, true));
            Identifier id = textureManager.registerDynamicTexture("aylyth_tulpa/" + base.getPath(), texture);
            this.renderLayer = RenderLayer.getEntityCutout(id);
            this.needsUpdate = true;
        }

        public void updateTexture() {
            MinecraftClient.getInstance().execute(() -> {
                try {
                    NativeImage inputImage;
                    if (!playerSkin) {
                        inputImage = NativeImage.read(resourceManager.getResource(base).get().getInputStream());
                    } else {
                        inputImage = getPlayerSkin(base);
                    }
                    Optional<Resource> optionalResource = resourceManager.getResource(TULPA_TEXTURE);
                    if(optionalResource.isPresent()){
                        NativeImage tuplaImage = NativeImage.read(optionalResource.get().getInputStream());
                        for (int y = 0; y < 128; y++) {
                            for (int x = 0; x < 128; x++) {
                                int color = tuplaImage.getColor(x, y);
                                texture.getImage().setColor(x, y, color);
                            }
                        }
                    }
                    for (int y = 0; y < 64; y++) {
                        for (int x = 0; x < 64; x++) {
                            int color = inputImage.getColor(x, y);
                            texture.getImage().setColor(x, y, color);
                        }
                    }
                    needsUpdate = false;
                    inputImage.close();
                    this.texture.upload();
                } catch (Exception e) {
                    if (e instanceof FileNotFoundException) {
                        LOGGER.info("Retrieving player texture file...");
                    } else {
                        LOGGER.error("Could not update stone texture.", e);
                    }
                }
            });
        }

        private NativeImage getPlayerSkin(Identifier base) throws IOException {
            PlayerSkinTexture skinTexture = (PlayerSkinTexture) textureManager.getTexture(base);
            PlayerSkinTextureAccessor accessor = (PlayerSkinTextureAccessor) skinTexture;
            File file = accessor.getCacheFile();
            return accessor.invokeRemapTexture(NativeImage.read(new FileInputStream(file)));
        }


        @Override
        public void close() {
            this.texture.close();
        }

    }

    // TODO remove
    @Override
    public void preRender(MatrixStack stackIn, TulpaEntity animatable, BakedGeoModel model, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, boolean isReRender, float ticks, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.rtb = renderTypeBuffer;
        this.whTexture = this.getTexture(animatable);
        super.preRender(stackIn, animatable, model, renderTypeBuffer, vertexBuilder, isReRender, ticks, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(MatrixStack stack, TulpaEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        var mainHand = animatable.getMainHandStack();
        var offHand = animatable.getOffHandStack();

        if (bone.getName().equals("rightItem") && !mainHand.isEmpty()) {
            stack.push();
            stack.translate(0.25,0.4,0.05);
            stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, rtb, null, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }else if (bone.getName().equals("leftItem") && !offHand.isEmpty()) {
            stack.push();
            stack.translate(-0.25,0.4,0.05);
            stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
            stack.scale(1.0f, 1.0f, 1.0f);
            if(offHand.isOf(Items.SHIELD)){
                stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
                stack.translate(0,0.2,-1.4);
            }
            MinecraftClient.getInstance().getItemRenderer().renderItem(offHand, ModelTransformationMode.THIRD_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, null, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(TulpaEntity entityLivingBaseIn) {
        return 0.0F;
    }

    private void copyEntityStateAndRender(MatrixStack matrixStack, TulpaEntity entity, float entityYaw, float partialTicks, VertexConsumerProvider bufferIn, int packedLightIn){
        matrixStack.push();
        if(tulpaPlayerEntity == null) {
            tulpaPlayerEntity = AylythEntityTypes.TULPA_PLAYER.create(entity.getWorld());
        }else{
            tulpaPlayerEntity.age = entity.age;
            tulpaPlayerEntity.hurtTime = entity.hurtTime;
            tulpaPlayerEntity.maxHurtTime = Integer.MAX_VALUE;
            // TODO copy LivingEntity::limbAnimator
            // tulpaPlayerEntity.limbDistance = entity.limbDistance;
            // tulpaPlayerEntity.lastLimbDistance = entity.lastLimbDistance;
            // tulpaPlayerEntity.limbAngle = entity.limbAngle;
            tulpaPlayerEntity.headYaw = entity.headYaw;
            tulpaPlayerEntity.prevHeadYaw = entity.prevHeadYaw;
            tulpaPlayerEntity.bodyYaw = entity.bodyYaw;
            tulpaPlayerEntity.prevBodyYaw = entity.prevBodyYaw;
            tulpaPlayerEntity.handSwinging = entity.handSwinging;
            tulpaPlayerEntity.handSwingTicks = entity.handSwingTicks;
            tulpaPlayerEntity.handSwingProgress = entity.handSwingProgress;
            tulpaPlayerEntity.lastHandSwingProgress = entity.lastHandSwingProgress;
            tulpaPlayerEntity.setPitch(entity.getPitch());
            tulpaPlayerEntity.prevPitch = entity.prevPitch;
            tulpaPlayerEntity.preferredHand = entity.preferredHand;
            tulpaPlayerEntity.setStackInHand(Hand.MAIN_HAND, entity.getMainHandStack());
            tulpaPlayerEntity.setStackInHand(Hand.OFF_HAND, entity.getOffHandStack());
            tulpaPlayerEntity.equipStack(EquipmentSlot.HEAD, entity.getEquippedStack(EquipmentSlot.HEAD));
            tulpaPlayerEntity.equipStack(EquipmentSlot.CHEST, entity.getEquippedStack(EquipmentSlot.CHEST));
            tulpaPlayerEntity.equipStack(EquipmentSlot.LEGS, entity.getEquippedStack(EquipmentSlot.LEGS));
            tulpaPlayerEntity.equipStack(EquipmentSlot.FEET, entity.getEquippedStack(EquipmentSlot.FEET));
            tulpaPlayerEntity.setCurrentHand(entity.getActiveHand() == null ? Hand.MAIN_HAND : entity.getActiveHand());
            tulpaPlayerEntity.setSneaking(entity.isSneaking());
            tulpaPlayerEntity.isSneaking();
            tulpaPlayerEntity.forwardSpeed=entity.forwardSpeed;
            tulpaPlayerEntity.setPose(entity.getPose());
            tulpaPlayerEntity.setSprinting(entity.isSprinting());
            tulpaPlayerEntity.setSkinUuid(entity.getSkinUuid());
            tulpaPlayerEntity.setCustomName(entity.getCustomName());
//            tulpaPlayerEntity.setUsingItem(entity.getItemUseTime() > 0);

            tulpaPlayerEntity.prevCapeX = entity.prevCapeX;
            tulpaPlayerEntity.prevCapeY = entity.prevCapeY;
            tulpaPlayerEntity.prevCapeZ = entity.prevCapeZ;
            tulpaPlayerEntity.capeX = entity.capeX;
            tulpaPlayerEntity.capeY = entity.capeY;
            tulpaPlayerEntity.capeZ = entity.capeZ;
            tulpaPlayerEntity.strideDistance = entity.strideDistance;
            tulpaPlayerEntity.prevStrideDistance = entity.prevStrideDistance;

            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(tulpaPlayerEntity).render(tulpaPlayerEntity, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
        }
        matrixStack.pop();
    }
}
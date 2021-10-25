package moriyashiine.aylyth.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.component.entity.YmpeInfestationComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Unique
	private static final Identifier YMPE_OUTLINE_0_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_0.png");
	
	@Unique
	private static final Identifier YMPE_OUTLINE_1_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_1.png");
	
	@Unique
	private static final Identifier YMPE_OUTLINE_2_TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/gui/ympe_outline_2.png");
	
	@Unique
	private static final Identifier YMPE_HEALTH_TEXTURES = new Identifier(Aylyth.MOD_ID, "textures/gui/icons.png");
	
	@Unique
	private boolean shouldRebind = false;
	
	@Shadow
	@Final
	private MinecraftClient client;
	
	@Shadow
	protected abstract void renderOverlay(Identifier texture, float opacity);
	
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 1))
	private void renderYmpeInfestationOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		ModComponents.YMPE_INFESTATION.maybeGet(client.player).ifPresent(ympeInfestationComponent -> {
			int stage = ympeInfestationComponent.getStage();
			if (stage >= 3) {
				renderOverlay(YMPE_OUTLINE_1_TEXTURE, stage == 3 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
			if (stage >= 2) {
				renderOverlay(YMPE_OUTLINE_0_TEXTURE, stage == 2 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
			if (stage >= 5) {
				renderOverlay(YMPE_OUTLINE_2_TEXTURE, stage == 5 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
		});
	}
	
	@Inject(method = "renderHealthBar", at = @At("HEAD"))
	private void renderYmpeHealthBarHead(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
		if (ModComponents.YMPE_INFESTATION.get(player).getStage() > 0) {
			RenderSystem.setShaderTexture(0, YMPE_HEALTH_TEXTURES);
			shouldRebind = true;
		}
	}
	
	@Inject(method = "renderHealthBar", at = @At("TAIL"))
	private void renderYmpeHealthBarTail(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
		if (shouldRebind) {
			RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
			shouldRebind = false;
		}
	}
}

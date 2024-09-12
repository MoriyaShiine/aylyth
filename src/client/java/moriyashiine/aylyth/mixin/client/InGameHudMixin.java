package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.api.interfaces.AylythGameHud;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.entity.component.YmpeInfestationComponent;
import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import moriyashiine.aylyth.common.entity.attribute.AylythAttributes;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin implements AylythGameHud {
	@Shadow
	@Final
	private MinecraftClient client;
	
	@Shadow
	protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

	@Shadow protected abstract void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart);

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 1))
	private void renderYmpeInfestationOverlay(DrawContext context, float tickDelta, CallbackInfo ci) {
		AylythEntityComponents.YMPE_INFESTATION.maybeGet(client.player).ifPresent(ympeInfestationComponent -> {
			int stage = ympeInfestationComponent.getStage();
			if (stage >= 3) {
				renderOverlay(context, YMPE_OUTLINE_1_TEXTURE, stage == 3 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
			if (stage >= 2) {
				renderOverlay(context, YMPE_OUTLINE_0_TEXTURE, stage == 2 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
			if (stage >= 5) {
				renderOverlay(context, YMPE_OUTLINE_2_TEXTURE, stage == 5 ? (float) ympeInfestationComponent.getInfestationTimer() / YmpeInfestationComponent.TIME_UNTIL_STAGE_INCREASES : 1);
			}
		});

		// TODO: Make more efficient
		if (client.world.getBlockState(client.player.getBlockPos()).isIn(AylythBlockTags.SEEPS)) {
			renderOverlay(context, SEEP_OVERLAY, 1);
		}
	}

	@ModifyArg(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
	private Identifier drawBranchingHearts(Identifier id) {
		if (AylythEntityComponents.YMPE_INFESTATION.get(client.player).getStage() > 0) {
			return AylythGameHud.YMPE_HEALTH_TEXTURES;
		}
		return id;
	}

	@Inject(method = "renderHealthBar", at = @At("TAIL"))
	private void drawAylythHearts(DrawContext context, PlayerEntity player, int x, int y, int lines,
								  int regeneratingHeartIndex, float maxHealth, int lastHealth, int health,
								  int absorption, boolean blinking, CallbackInfo ci,
								  @Local(ordinal = 8) int j, @Local(ordinal = 9) int k) {
		int maxVitalHealth = (int) player.getAttributeValue(AylythAttributes.MAX_VITAL_HEALTH);
		if (maxVitalHealth == 0) {
			return;
		}
		float vitalHealth = VitalHealthHolder.of(player).map(VitalHealthHolder::getCurrentVitalHealth).orElse(0f);
		int heartsToDraw = MathHelper.ceil((double)maxVitalHealth / 2);
		int firstHeartIndex = j + k;
		for (int i = heartsToDraw + firstHeartIndex - 1; i >= firstHeartIndex; i--) {
			int actualX = x + (i % 10) * 8;
			int actualY = y - (i / 10) * lines;

			drawHeart(context, InGameHud.HeartType.CONTAINER, actualX, actualY, 0, blinking, false);

			int representedHealth = i*2;
			if (representedHealth < maxHealth+absorption+vitalHealth) {
				int u = 0;
				if (AylythEntityComponents.YMPE_INFESTATION.get(player).getStage() > 0) {
					u += 16;
				}
				if (representedHealth+1 == maxHealth+absorption+vitalHealth) {
					u += 9;
				}
				context.drawTexture(AylythGameHud.HEARTS, actualX+1, actualY+1, 0, u, 0, 7, 7, 64, 64);
			} else {
				context.drawTexture(AylythGameHud.HEARTS, actualX+1, actualY+1, 0, 32, 0, 7, 7, 64, 64);
			}
		}
	}
}

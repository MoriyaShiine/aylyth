package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.api.interfaces.AylythGameHud;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
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

	@Shadow protected abstract void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half);

	@ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I", ordinal = 2))
	private float modifyArmorRenderLocation(float original, @Local float health, @Local(ordinal = 5) int absorption) {
		float vitalHealth = (float) client.player.getAttributeValue(AylythAttributes.MAX_VITAL_HEALTH);
		if (vitalHealth > 0) {
			return (health + absorption + vitalHealth) / 2.0f / 10.0f;
		}
		return original;
	}

	@Inject(method = "drawHeart", at = @At("TAIL"))
	private void drawBranches(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
		YmpeInfestation infestation = client.player.getAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION);
		if (infestation != null && infestation.getStage() > 0) {
			Identifier texture = type == InGameHud.HeartType.CONTAINER || type == InGameHud.HeartType.WITHERED
					? half ? BRANCHES_CONTAINER_HALF : BRANCHES_CONTAINER_FULL
					: half ? BRANCHES_HALF : BRANCHES_FULL;
			context.drawGuiTexture(RenderLayer::getGuiTextured, texture, x, y, 9, 9);
		}
	}

	@Inject(method = "renderHealthBar", at = @At("TAIL"))
	private void drawAylythHearts(DrawContext context, PlayerEntity player, int x, int y, int lines,
								  int regeneratingHeartIndex, float maxHealth, int lastHealth, int health,
								  int absorption, boolean blinking, CallbackInfo ci,
								  @Local(ordinal = 7) int i, @Local(ordinal = 8) int j) {
		int maxVitalHealth = (int) player.getAttributeValue(AylythAttributes.MAX_VITAL_HEALTH);
		if (maxVitalHealth == 0) {
			return;
		}
		float vitalHealth = VitalHealthHolder.of(player).map(VitalHealthHolder::getCurrentVitalHealth).orElse(0f);
		int heartsToDraw = MathHelper.ceil((double)maxVitalHealth / 2);
		int firstHeartIndex = i + j;
		for (int l = heartsToDraw + firstHeartIndex - 1; l >= firstHeartIndex; l--) {
			int actualX = x + (l % 10) * 8;
			int actualY = y - (l / 10) * lines;

			drawHeart(context, InGameHud.HeartType.CONTAINER, actualX, actualY, false, blinking, false);

			YmpeInfestation infestation = player.getAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION);
			boolean hasBranches = infestation != null && infestation.getStage() > 0;
			int representedHealth = l * 2;
			if (representedHealth < maxHealth+absorption+vitalHealth) {
				boolean half = representedHealth + 1 == maxHealth + absorption + vitalHealth;
				context.drawGuiTexture(RenderLayer::getGuiTextured, half ? VITAL_HALF : VITAL_FULL, actualX, actualY, 9, 9);
				if (hasBranches) {
					context.drawGuiTexture(RenderLayer::getGuiTextured, half ? BRANCHES_HALF : BRANCHES_FULL, actualX, actualY, 9, 9);
				}
			} else {
				context.drawGuiTexture(RenderLayer::getGuiTextured, VITAL_CONTAINER_FULL, actualX, actualY, 9, 9);
				if (hasBranches) {
					context.drawGuiTexture(RenderLayer::getGuiTextured, BRANCHES_CONTAINER_FULL, actualX, actualY, 9, 9);
				}
			}
		}
	}
}

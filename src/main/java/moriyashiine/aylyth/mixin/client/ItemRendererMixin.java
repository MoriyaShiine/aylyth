package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.common.item.YmpeGlaiveItem;
import moriyashiine.aylyth.common.item.YmpeLanceItem;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
final class ItemRendererMixin {
	@Shadow @Final private ItemModels models;

	@Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
	private void aylyth_getHeldItemModel(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
		if (stack.getItem() instanceof YmpeLanceItem || stack.getItem() instanceof YmpeGlaiveItem) {
			BakedModel bakedModel = models.getModelManager().getModel(ModelIdentifier.ofVanilla("trident_in_hand", "inventory")); // this is the model type (not the texture), its insane that copy-pasting this works first try
			ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;
			BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, seed);
			cir.setReturnValue(bakedModel2 == null ? this.models.getModelManager().getMissingModel() : bakedModel2);
		}
	}
}

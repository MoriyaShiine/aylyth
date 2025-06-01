package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class FireRitualCraftingEvents {
    public static void init() {
        UseBlockCallback.EVENT.register(FireRitualCraftingEvents::interactSoulCampfire);
    }

    private static ActionResult interactSoulCampfire(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
        if (world instanceof ServerWorld serverWorld) {
            if (hand == Hand.MAIN_HAND && world.getBlockState(blockHitResult.getBlockPos()).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockHitResult.getBlockPos()) instanceof CampfireBlockEntity campfireBlockEntity){
                ItemStack itemStack = playerEntity.getMainHandStack();
                // TODO: Check this works
                // TODO: Cache this?
                List<Ingredient> allowedIngredients = serverWorld.getRecipeManager().getAllOfType(AylythRecipeTypes.FIRE_RITUAL_TYPE).stream()
                        .map(entry -> entry.value().input)
                        .flatMap(Collection::stream)
                        .toList();
                if (allowedIngredients.stream().anyMatch(ingredient -> ingredient.test(itemStack))){
                    if (!world.isClient && campfireBlockEntity.addItem(serverWorld, playerEntity, itemStack)) {
                        playerEntity.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}

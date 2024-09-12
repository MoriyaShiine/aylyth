package moriyashiine.aylyth.common.recipe;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.registry.AylythRecipeTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class YmpeDaggerDropRecipe implements Recipe<Inventory> {
	private final Identifier identifier;
	public final EntityType<?> entity_type;
	private final ItemStack output;
	public final float chance;
	public final int min;
	public final int max;
	
	public YmpeDaggerDropRecipe(Identifier id, EntityType<?> entity_type, ItemStack output, float chance, int min, int max) {
		this.identifier = id;
		this.entity_type = entity_type;
		this.output = output;
		this.chance = chance;
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean matches(Inventory inventory, World world) {
		return false;
	}
	
	@Override
	public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
		return output;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return false;
	}
	
	@Override
	public ItemStack getOutput(DynamicRegistryManager registryManager) {
		return output;
	}
	
	@Override
	public Identifier getId() {
		return identifier;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return AylythRecipeTypes.YMPE_DAGGER_DROP_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return AylythRecipeTypes.YMPE_DAGGER_DROP_TYPE;
	}
	
	public static class Serializer implements RecipeSerializer<YmpeDaggerDropRecipe> {
		@Override
		public YmpeDaggerDropRecipe read(Identifier id, JsonObject json) {
			return new YmpeDaggerDropRecipe(
					id,
					Registries.ENTITY_TYPE.get(new Identifier(JsonHelper.getString(json, "entity_type"))),
					ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")),
					JsonHelper.getFloat(json, "chance"),
					JsonHelper.getInt(json, "min"),
					JsonHelper.getInt(json, "max")
			);
		}
		
		@Override
		public YmpeDaggerDropRecipe read(Identifier id, PacketByteBuf buf) {
			return new YmpeDaggerDropRecipe(
					id,
					buf.readRegistryValue(Registries.ENTITY_TYPE),
					buf.readItemStack(),
					buf.readFloat(),
					buf.readInt(),
					buf.readInt()
			);
		}
		
		@Override
		public void write(PacketByteBuf buf, YmpeDaggerDropRecipe recipe) {
			buf.writeRegistryValue(Registries.ENTITY_TYPE, recipe.entity_type);
			buf.writeItemStack(recipe.output);
			buf.writeFloat(recipe.chance);
			buf.writeInt(recipe.min);
			buf.writeInt(recipe.max);
		}
	}
}

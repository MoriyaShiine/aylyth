package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.data.tag.AylythDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public final class AylythDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public AylythDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.DAMAGE_TYPE, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(AylythDamageTypes.YMPE, AylythDamageTypes.KILLING_BLOW, AylythDamageTypes.SOUL_RIP, AylythDamageTypes.BLIGHT);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(AylythDamageTypes.YMPE, AylythDamageTypes.KILLING_BLOW, AylythDamageTypes.BLIGHT);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(AylythDamageTypes.YMPE, AylythDamageTypes.KILLING_BLOW, AylythDamageTypes.BLIGHT);
//            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.UNBLOCKABLE);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(AylythDamageTypes.KILLING_BLOW);
        getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO).add(AylythDamageTypes.SOUL_RIP);
        getOrCreateTagBuilder(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(AylythDamageTypes.SOUL_RIP);
        getOrCreateTagBuilder(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(AylythDamageTypes.SOUL_RIP);
        getOrCreateTagBuilder(AylythDamageTypeTags.IS_YMPE).add(AylythDamageTypes.YMPE, AylythDamageTypes.YMPE_ENTITY);
        getOrCreateTagBuilder(AylythDamageTypeTags.BYPASSES_CUIRASS).addOptionalTag(DamageTypeTags.IS_FALL).add(DamageTypes.MAGIC, DamageTypes.OUT_OF_WORLD);
        getOrCreateTagBuilder(AylythDamageTypeTags.DEATH_SENDS_TO_AYLYTH).addOptionalTag(DamageTypeTags.IS_FALL).addOptionalTag(DamageTypeTags.IS_DROWNING);
    }
}
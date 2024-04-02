package moriyashiine.aylyth.datagen.tags;

import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.tag.ModDamageTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class AylythDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public AylythDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.DAMAGE_TYPE, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW, ModDamageTypeKeys.SOUL_RIP, ModDamageTypeKeys.BLIGHT);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW, ModDamageTypeKeys.BLIGHT);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW, ModDamageTypeKeys.BLIGHT);
//            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.UNBLOCKABLE);
        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(ModDamageTypeKeys.KILLING_BLOW);
        getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO).add(ModDamageTypeKeys.SOUL_RIP);
        getOrCreateTagBuilder(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(ModDamageTypeKeys.SOUL_RIP);
        getOrCreateTagBuilder(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(ModDamageTypeKeys.SOUL_RIP);
        getOrCreateTagBuilder(ModDamageTypeTags.IS_YMPE).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.YMPE_ENTITY);
    }
}
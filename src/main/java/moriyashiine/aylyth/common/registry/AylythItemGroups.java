package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public interface AylythItemGroups {

    RegistryKey<ItemGroup> MAIN = RegistryKey.of(RegistryKeys.ITEM_GROUP, AylythUtil.id(Aylyth.MOD_ID));

    static void register() {
        Registry.register(Registries.ITEM_GROUP, MAIN, FabricItemGroup.builder()
                .icon(() -> new ItemStack(AylythItems.YMPE_DAGGER))
                .displayName(Text.translatable("itemGroup.aylyth.main"))
                .entries((displayContext, entries) -> Registries.ITEM.forEach(item -> {
                    if (Registries.ITEM.getId(item).getNamespace().equals(Aylyth.MOD_ID)) {
                        entries.add(item);
                    }
                }))
                .build()
        );
    }
}

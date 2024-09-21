package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.List;

public class AylythianSmithingTemplateUpgradeItem extends SmithingTemplateItem {
    public AylythianSmithingTemplateUpgradeItem() {
        super(
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", Aylyth.id("aylythian_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.additions_slot_description"))),
                List.of(Aylyth.id("item/empty_slot_ympe_sapling")),
                List.of(Aylyth.id("item/empty_slot_esstline"))
        );
    }
}

package moriyashiine.aylyth.common.item.type;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.List;

public class AylythianSmithingTemplateUpgradeItem extends SmithingTemplateItem {
    public AylythianSmithingTemplateUpgradeItem() {
        super(
                Text.translatable(Util.createTranslationKey("item", AylythUtil.id("smithing_template.aylythian_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", AylythUtil.id("smithing_template.aylythian_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("upgrade", AylythUtil.id("aylythian_upgrade"))).formatted(Formatting.GRAY),
                Text.translatable(Util.createTranslationKey("item", AylythUtil.id("smithing_template.aylythian_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", AylythUtil.id("smithing_template.aylythian_upgrade.additions_slot_description"))),
                List.of(AylythUtil.id("item/empty_slot_ympe_sapling")),
                List.of(AylythUtil.id("item/empty_slot_esstline"))
        );
    }
}

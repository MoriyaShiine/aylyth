package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.List;

public class AylythianSmithingTemplateUpgradeItem extends SmithingTemplateItem {
    public AylythianSmithingTemplateUpgradeItem(Settings settings) {
        super(
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.applies_to"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.ingredients"))).formatted(Formatting.BLUE),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.base_slot_description"))),
                Text.translatable(Util.createTranslationKey("item", Aylyth.id("smithing_template.aylythian_upgrade.additions_slot_description"))),
                List.of(
                        Aylyth.id("container/slot/ympe_sapling"),
                        Aylyth.id("container/slot/nephrite_axe"),
                        Aylyth.id("container/slot/nephrite_hoe"),
                        Aylyth.id("container/slot/nephrite_pickaxe"),
                        Aylyth.id("container/slot/nephrite_sword")
                ),
                List.of(
                        Aylyth.id("container/slot/esstline"),
                        Aylyth.id("container/slot/aylythian_heart"),
                        Aylyth.id("container/slot/blighted_thorns")
                ),
                settings
        );
    }
}

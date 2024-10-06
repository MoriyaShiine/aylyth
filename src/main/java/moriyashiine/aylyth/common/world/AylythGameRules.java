package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

public interface AylythGameRules {

    CustomGameRuleCategory MAIN_CATEGORY = new CustomGameRuleCategory(Aylyth.id("aylyth"), Text.translatable("gamerule.category.aylyth.main").setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));

    /** Determines whether green pilot lights spawn when entering Aylyth via forest death */
    GameRules.Key<GameRules.BooleanRule> ESCAPE_LIGHTS = GameRuleRegistry.register("aylyth.spawn_green_pilot_lights", MAIN_CATEGORY, GameRuleFactory.createBooleanRule(true));

    static void register() {}
}

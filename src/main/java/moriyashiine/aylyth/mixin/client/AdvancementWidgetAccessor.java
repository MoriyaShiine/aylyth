package moriyashiine.aylyth.mixin.client;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(AdvancementWidget.class)
public interface AdvancementWidgetAccessor {

    @Accessor("WIDGETS_TEXTURE")
    static Identifier getWidgetsTexture() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    AdvancementDisplay getDisplay();

    @Accessor
    AdvancementProgress getProgress();

    @Accessor
    List<AdvancementWidget> getChildren();

    @Accessor
    MinecraftClient getClient();

    @Accessor("x")
    int getX();

    @Accessor("y")
    int getY();

    @Accessor
    int getWidth();

    @Accessor
    Advancement getAdvancement();

    @Accessor
    OrderedText getTitle();

    @Accessor
    AdvancementTab getTab();

    @Accessor
    List<OrderedText> getDescription();

    @Accessor
    AdvancementWidget getParent();
}

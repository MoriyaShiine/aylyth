package moriyashiine.aylyth.mixin;

import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AdvancementDisplay.class)
public interface AdvancementDisplayAccessor {

    @Accessor
    Text getTitle();

    @Accessor
    Text getDescription();

    @Accessor
    AdvancementFrame getFrame();

    @Accessor
    boolean getShowToast();

    @Accessor
    boolean getAnnounceToChat();

    @Accessor
    boolean getHidden();

    @Accessor
    Identifier getBackground();

    @Accessor("x")
    float getX();

    @Accessor("y")
    float getY();
}

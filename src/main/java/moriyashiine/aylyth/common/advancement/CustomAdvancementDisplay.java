package moriyashiine.aylyth.common.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import moriyashiine.aylyth.client.advancement.CustomAdvancementWidget;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.mixin.AdvancementDisplayAccessor;
import moriyashiine.aylyth.mixin.client.AdvancementWidgetAccessor;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

public class CustomAdvancementDisplay extends AdvancementDisplay {

    private final Identifier texture;

    public CustomAdvancementDisplay(Identifier texture, Text title, Text description, @Nullable Identifier background, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
        super(new ItemStack(Items.AIR), title, description, background, frame, showToast, announceToChat, hidden);
        this.texture = texture;
    }

    public Identifier getTexture() {
        return texture;
    }

    @Override
    public JsonElement toJson() {
        var accessor = (AdvancementDisplayAccessor)this;
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("title", Text.Serializer.toJsonTree(accessor.getTitle()));
        jsonObject.add("description", Text.Serializer.toJsonTree(accessor.getDescription()));
        jsonObject.addProperty("texture", texture.toString());
        jsonObject.addProperty("frame", accessor.getFrame().getId());
        jsonObject.addProperty("show_toast", accessor.getShowToast());
        jsonObject.addProperty("announce_to_chat", accessor.getAnnounceToChat());
        jsonObject.addProperty("hidden", accessor.getHidden());
        if (accessor.getBackground() != null) {
            jsonObject.addProperty("background", accessor.getBackground().toString());
        }

        return jsonObject;
    }

    public static CustomAdvancementDisplay fromJson(JsonObject obj) {
        Text title = Text.Serializer.fromJson(obj.get("title"));
        Text desc = Text.Serializer.fromJson(obj.get("description"));
        if (title != null && desc != null) {
            Identifier background = obj.has("background") ? new Identifier(JsonHelper.getString(obj, "background")) : null;
            Identifier texture = new Identifier(JsonHelper.getString(obj, "texture"));
            AdvancementFrame advancementFrame = obj.has("frame") ? AdvancementFrame.forName(JsonHelper.getString(obj, "frame")) : AdvancementFrame.TASK;
            boolean showToast = JsonHelper.getBoolean(obj, "show_toast", true);
            boolean announce = JsonHelper.getBoolean(obj, "announce_to_chat", true);
            boolean hidden = JsonHelper.getBoolean(obj, "hidden", false);
            return new CustomAdvancementDisplay(texture, title, desc, background, advancementFrame, showToast, announce, hidden);
        } else {
            throw new JsonSyntaxException("Both title and description must be set");
        }
    }

    @Override
    public void toPacket(PacketByteBuf buf) {
        var accessor = (AdvancementDisplayAccessor)this;
        buf.writeVarInt(-1);
        buf.writeText(accessor.getTitle());
        buf.writeText(accessor.getDescription());
        buf.writeIdentifier(texture);
        buf.writeEnumConstant(accessor.getFrame());
        int i = 0;
        if (accessor.getBackground() != null) {
            i |= 1;
        }

        if (accessor.getShowToast()) {
            i |= 2;
        }

        if (accessor.getHidden()) {
            i |= 4;
        }

        buf.writeInt(i);
        if (accessor.getBackground() != null) {
            buf.writeIdentifier(accessor.getBackground());
        }

        buf.writeFloat(accessor.getX());
        buf.writeFloat(accessor.getY());
    }

    public static CustomAdvancementDisplay fromPacket(PacketByteBuf buf) {
        Text text = buf.readText();
        Text text2 = buf.readText();
        Identifier texture = buf.readIdentifier();
        AdvancementFrame advancementFrame = buf.readEnumConstant(AdvancementFrame.class);
        int i = buf.readInt();
        Identifier identifier = (i & 1) != 0 ? buf.readIdentifier() : null;
        boolean bl = (i & 2) != 0;
        boolean bl2 = (i & 4) != 0;
        CustomAdvancementDisplay advancementDisplay = new CustomAdvancementDisplay(texture, text, text2, identifier, advancementFrame, bl, false, bl2);
        advancementDisplay.setPos(buf.readFloat(), buf.readFloat());
        return advancementDisplay;
    }

    public interface Renderer {

        void render(MatrixStack matrices, int x, int y, CustomAdvancementWidget widget);
    }
}

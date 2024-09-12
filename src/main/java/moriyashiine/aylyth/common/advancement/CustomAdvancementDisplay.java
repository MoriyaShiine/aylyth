package moriyashiine.aylyth.common.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.AdvancementDisplayAccessor;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

public class CustomAdvancementDisplay extends AdvancementDisplay {

    private final AdvancementRendererData rendererData;

    public CustomAdvancementDisplay(AdvancementRendererData rendererData, Text title, Text description, @Nullable Identifier background, AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
        super(ItemStack.EMPTY, title, description, background, frame, showToast, announceToChat, hidden);
        this.rendererData = rendererData;
    }

    public AdvancementRendererData getRendererData() {
        return rendererData;
    }

    @Override
    public JsonElement toJson() {
        AdvancementDisplayAccessor accessor = (AdvancementDisplayAccessor)this;
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("title", Text.Serializer.toJsonTree(accessor.getTitle()));
        jsonObject.add("description", Text.Serializer.toJsonTree(accessor.getDescription()));
        JsonElement renderer = AdvancementRendererDataType.CODEC.encodeStart(JsonOps.INSTANCE, rendererData).getOrThrow(false, Aylyth.LOGGER::error);
        jsonObject.add("renderer", renderer);
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
            JsonObject renderer = JsonHelper.getObject(obj, "renderer");
            AdvancementRendererData data = AdvancementRendererDataType.CODEC.parse(JsonOps.INSTANCE, renderer).getOrThrow(false, Aylyth.LOGGER::error);
            AdvancementFrame advancementFrame = obj.has("frame") ? AdvancementFrame.forName(JsonHelper.getString(obj, "frame")) : AdvancementFrame.TASK;
            boolean showToast = JsonHelper.getBoolean(obj, "show_toast", true);
            boolean announce = JsonHelper.getBoolean(obj, "announce_to_chat", true);
            boolean hidden = JsonHelper.getBoolean(obj, "hidden", false);
            return new CustomAdvancementDisplay(data, title, desc, background, advancementFrame, showToast, announce, hidden);
        } else {
            throw new JsonSyntaxException("Both title and description must be set");
        }
    }

    @Override
    public void toPacket(PacketByteBuf buf) {
        AdvancementDisplayAccessor accessor = (AdvancementDisplayAccessor)this;
        buf.writeVarInt(-1);
        buf.writeText(accessor.getTitle());
        buf.writeText(accessor.getDescription());
        buf.encodeAsJson(AdvancementRendererDataType.CODEC, rendererData);
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
        AdvancementRendererData data = buf.decodeAsJson(AdvancementRendererDataType.CODEC);
        AdvancementFrame advancementFrame = buf.readEnumConstant(AdvancementFrame.class);
        int i = buf.readInt();
        Identifier identifier = (i & 1) != 0 ? buf.readIdentifier() : null;
        boolean bl = (i & 2) != 0;
        boolean bl2 = (i & 4) != 0;
        CustomAdvancementDisplay advancementDisplay = new CustomAdvancementDisplay(data, text, text2, identifier, advancementFrame, bl, false, bl2);
        advancementDisplay.setPos(buf.readFloat(), buf.readFloat());
        return advancementDisplay;
    }
}

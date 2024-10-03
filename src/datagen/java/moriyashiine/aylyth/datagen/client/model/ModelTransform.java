package moriyashiine.aylyth.datagen.client.model;

import net.minecraft.util.StringIdentifiable;

/**
 * Data-safe version of {@link net.minecraft.client.render.model.json.ModelTransformationMode}
 */
public enum ModelTransform implements StringIdentifiable {
    NONE("none"),
    THIRD_PERSON_LEFT_HAND("thirdperson_lefthand"),
    THIRD_PERSON_RIGHT_HAND("thirdperson_righthand"),
    FIRST_PERSON_LEFT_HAND("firstperson_lefthand"),
    FIRST_PERSON_RIGHT_HAND("firstperson_righthand"),
    HEAD("head"),
    GUI("gui"),
    GROUND("ground"),
    FIXED("fixed");

    public static final com.mojang.serialization.Codec<ModelTransform> CODEC = StringIdentifiable.createCodec(ModelTransform::values);

    private final String name;

    ModelTransform(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}

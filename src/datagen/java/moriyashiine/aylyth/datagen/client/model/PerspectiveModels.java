package moriyashiine.aylyth.datagen.client.model;

public class PerspectiveModels {
    public static final PerspectiveModelData BIG_HANDHELD = PerspectiveModelData.builder(ModelKeys.GUI)
            .withModel(ModelTransform.FIRST_PERSON_LEFT_HAND, ModelKeys.HANDHELD)
            .withModel(ModelTransform.FIRST_PERSON_RIGHT_HAND, ModelKeys.HANDHELD)
            .withModel(ModelTransform.THIRD_PERSON_LEFT_HAND, ModelKeys.HANDHELD)
            .withModel(ModelTransform.THIRD_PERSON_RIGHT_HAND, ModelKeys.HANDHELD)
            .build();
}
package moriyashiine.aylyth.datagen.client.model;

public class PerspectiveModels {
    public static final PerspectiveModelData BIG_HANDHELD = PerspectiveModelData.builder(PerspectiveModelKeys.GUI)
            .withModel(ModelDisplayType.FIRST_PERSON_LEFT_HAND, PerspectiveModelKeys.HANDHELD)
            .withModel(ModelDisplayType.FIRST_PERSON_RIGHT_HAND, PerspectiveModelKeys.HANDHELD)
            .withModel(ModelDisplayType.THIRD_PERSON_LEFT_HAND, PerspectiveModelKeys.HANDHELD)
            .withModel(ModelDisplayType.THIRD_PERSON_RIGHT_HAND, PerspectiveModelKeys.HANDHELD)
            .build();
}
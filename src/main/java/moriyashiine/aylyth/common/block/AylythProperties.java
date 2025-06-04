package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.block.types.SeepBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;

public interface AylythProperties {
    BooleanProperty GLOWING = BooleanProperty.of("glowing");
    BooleanProperty BUSHY = BooleanProperty.of("bushy");
    BooleanProperty FAILED = BooleanProperty.of("failed");
    BooleanProperty HARVESTABLE = BooleanProperty.of("harvestable");
    IntProperty AGE_4_STAGES = IntProperty.of("age", 0, 3);
    IntProperty AGE_5_STAGES = IntProperty.of("age", 0, 4);
    IntProperty FRUITING = IntProperty.of("fruiting", 0, 3);
    IntProperty CHARGES_6_STAGES = IntProperty.of("charges", 0, 5);
    IntProperty STAGES_3 = IntProperty.of("stage", 1, 3);
    IntProperty LEAVES = IntProperty.of("leaves", 0, 7);
    Property<SeepBlock.Connection> SEEP_CONNECTION = EnumProperty.of("connection", SeepBlock.Connection.class, SeepBlock.Connection.values());
}

package moriyashiine.aylyth.common.registry;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.generator.treedecorator.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.generator.treedecorator.RangedTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class ModTreeDecoratorTypes {
    public static void init() {}
    
    public static final TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = register("grape_vine_decorator", GrapeVineDecorator.CODEC);
    public static final TreeDecoratorType<RangedTreeDecorator> RANGED = register("ranged_tree_decorator", RangedTreeDecorator.CODEC);
    
    private static <T extends TreeDecorator> TreeDecoratorType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.TREE_DECORATOR_TYPE, AylythUtil.id(id), new TreeDecoratorType<>(codec));
    }
}

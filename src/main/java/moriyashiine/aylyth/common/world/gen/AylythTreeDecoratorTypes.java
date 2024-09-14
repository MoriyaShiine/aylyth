package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.gen.treedecorator.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.gen.treedecorator.RangedTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public interface AylythTreeDecoratorTypes {

    // TODO remove "_decorator"
    TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = register("grape_vine_decorator", GrapeVineDecorator.CODEC);
    TreeDecoratorType<RangedTreeDecorator> RANGED = register("ranged_tree_decorator", RangedTreeDecorator.CODEC);

    private static <D extends TreeDecorator> TreeDecoratorType<D> register(String name, Codec<D> codec) {
        return Registry.register(Registries.TREE_DECORATOR_TYPE, AylythUtil.id(name), new TreeDecoratorType<>(codec));
    }

    // Load static initializer
    static void register() {}
}

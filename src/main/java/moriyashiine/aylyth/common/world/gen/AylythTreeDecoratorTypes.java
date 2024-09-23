package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.treedecorators.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.gen.treedecorators.PlaceAroundTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public interface AylythTreeDecoratorTypes {

    TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = register("grape_vine", GrapeVineDecorator.CODEC);
    TreeDecoratorType<PlaceAroundTreeDecorator> PLACE_AROUND = register("place_around", PlaceAroundTreeDecorator.CODEC);

    private static <D extends TreeDecorator> TreeDecoratorType<D> register(String name, Codec<D> codec) {
        return Registry.register(Registries.TREE_DECORATOR_TYPE, Aylyth.id(name), new TreeDecoratorType<>(codec));
    }

    // Load static initializer
    static void register() {}
}

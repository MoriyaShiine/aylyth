package moriyashiine.aylyth.common.world.generator.feature;

//public class ShelfMushroomFeature extends Feature<ShelfMushroomFeature.ShelfMushroomFeatureConfig> {
//
//    public ShelfMushroomFeature() {
//        super(ShelfMushroomFeatureConfig.CODEC);
//    }
//
//    @Override
//    public boolean generate(FeatureContext<ShelfMushroomFeatureConfig> context) {
//        var world = context.getWorld();
//        var block = context.getConfig().block;
//        Optional<BlockState> state;
//        for (BlockPos pos : BlockPos.iterateRandomly(context.getRandom(), 25, context.getOrigin(), 4)) {
//            state = block.findGrowState(world, pos);
//            if (world.isAir(pos) && state.isPresent() && state.get().canPlaceAt(world, pos)) {
//                setBlockState(world, pos, state.get());
//            }
//        }
//        return true;
//    }
//
//    public static class ShelfMushroomFeatureConfig implements FeatureConfig {
//        public static final Codec<ShelfMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
//                Registry.BLOCK.getCodec().fieldOf("block").forGetter((config) -> config.block)
//        ).apply(instance, (ShelfMushroomFeatureConfig::new)));
//        public final ShelfMushroomBlock block;
//
//        public ShelfMushroomFeatureConfig(Block block) {
//            this.block = (ShelfMushroomBlock) block;
//        }
//    }
//}

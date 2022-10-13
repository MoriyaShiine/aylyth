package moriyashiine.aylyth.common.registry.block;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import moriyashiine.aylyth.mixin.BlocksAccessor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class WoodSuite {

    private final Identifier id;
    private final BlockSettingsSet blockSettingsSet;
    private final ItemSettingsSet itemSettingsSet;
    private final Registry<Block> blockRegistry;
    private final Registry<Item> itemRegistry;
    public final BlockWithItem strippedLog;
    public final BlockWithItem strippedWood;
    public final BlockWithItem log;
    public final BlockWithItem wood;
    public final BlockWithItem leaves;
    public final BlockWithItem sapling;
    public final BlockWithItem pottedSapling;
    public final BlockWithItem planks;
    public final BlockWithItem stairs;
    public final BlockWithItem slab;
    public final BlockWithItem fence;
    public final BlockWithItem fenceGate;
    public final BlockWithItem pressurePlate;
    public final BlockWithItem button;
    public final BlockWithItem trapdoor;
    public final BlockWithItem door;
    public final BlockWithItem floorSign;
    public final BlockWithItem wallSign;

    WoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
              @NotNull WoodSuite.ItemSettingsSet itemSettingsSet, @NotNull Registry<Block> blockRegistry,
              @NotNull Registry<Item> itemRegistry, @NotNull MapColor logColor, @NotNull BlockSoundGroup leavesSound,
              @NotNull SaplingGenerator saplingGenerator, BlockWithItem strippedLog, BlockWithItem strippedWood,
              BlockWithItem log, BlockWithItem wood, BlockWithItem leaves, BlockWithItem sapling,
              BlockWithItem pottedSapling, BlockWithItem planks, BlockWithItem stairs, BlockWithItem slab,
              BlockWithItem fence, BlockWithItem fenceGate, BlockWithItem pressurePlate, BlockWithItem button,
              BlockWithItem trapdoor, BlockWithItem door, BlockWithItem floorSign, BlockWithItem wallSign) {
        id = identifier;
        this.blockSettingsSet = blockSettingsSet;
        this.blockRegistry = blockRegistry;
        this.itemSettingsSet = itemSettingsSet;
        this.itemRegistry = itemRegistry;
        this.strippedLog = strippedLog;
        this.strippedWood = strippedWood;
        this.log = log;
        this.wood = wood;
        this.leaves = leaves;
        this.sapling = sapling;
        this.pottedSapling = pottedSapling;
        this.planks = planks;
        this.stairs = stairs;
        this.slab = slab;
        this.fence = fence;
        this.fenceGate = fenceGate;
        this.pressurePlate = pressurePlate;
        this.button = button;
        this.trapdoor = trapdoor;
        this.door = door;
        this.floorSign = floorSign;
        this.wallSign = wallSign;
    }

    WoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
              @NotNull WoodSuite.ItemSettingsSet itemSettingsSet, @NotNull Registry<Block> blockRegistry,
              @NotNull Registry<Item> itemRegistry, @NotNull MapColor logColor, @NotNull BlockSoundGroup leavesSound,
              @NotNull SaplingGenerator saplingGenerator) {
        id = identifier;
        this.blockSettingsSet = blockSettingsSet;
        this.blockRegistry = blockRegistry;
        this.itemSettingsSet = itemSettingsSet;
        this.itemRegistry = itemRegistry;
        strippedLog = BlockWithItem.of(new PillarBlock(blockSettingsSet.getStrippedLog()), block -> new BlockItem(block, itemSettingsSet.getStrippedLog()));
        strippedWood = BlockWithItem.of(new PillarBlock(blockSettingsSet.getStrippedWood()), block -> new BlockItem(block, itemSettingsSet.getStrippedWood()));
        log = BlockWithItem.of(new StrippableLogBlock(() -> strippedLog.block(), logColor, blockSettingsSet.getLog()), block -> new BlockItem(block, itemSettingsSet.getLog()));
        wood = BlockWithItem.of(new StrippableLogBlock(() -> strippedWood.block(), logColor, blockSettingsSet.getWood()), block -> new BlockItem(block, itemSettingsSet.getWood()));
        leaves = BlockWithItem.of(BlocksAccessor.callCreateLeavesBlock(leavesSound), block -> new BlockItem(block, itemSettingsSet.getLeaves()));
        sapling = BlockWithItem.of(new SaplingBlock(saplingGenerator, blockSettingsSet.getSapling()), block -> new BlockItem(block, itemSettingsSet.getSapling()));
        pottedSapling = BlockWithItem.of(new FlowerPotBlock(sapling.block(), blockSettingsSet.getPottedSapling()), block -> new BlockItem(block, itemSettingsSet.getPottedSapling()));
        planks = BlockWithItem.of(new Block(blockSettingsSet.getPlanks()), block -> new BlockItem(block, itemSettingsSet.getPlanks()));
        stairs = BlockWithItem.of(() -> new StairsBlock(planks.block().getDefaultState(), blockSettingsSet.getStairs()), block -> () -> new BlockItem(block.get(), itemSettingsSet.getStairs()));
        slab = BlockWithItem.of(new SlabBlock(blockSettingsSet.getSlab()), block -> new BlockItem(block, itemSettingsSet.getSlab()));
        fence = BlockWithItem.of(new FenceBlock(blockSettingsSet.getFence()), block -> new BlockItem(block, itemSettingsSet.getFence()));
        fenceGate = BlockWithItem.of(new FenceGateBlock(blockSettingsSet.getFenceGate()), block -> new BlockItem(block, itemSettingsSet.getFenceGate()));
        pressurePlate = BlockWithItem.of(new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, blockSettingsSet.getPressurePlate()), block -> new BlockItem(block, itemSettingsSet.getPressurePlate()));
        button = BlockWithItem.of(new WoodenButtonBlock(blockSettingsSet.getButton()), block -> new BlockItem(block, itemSettingsSet.getButton()));
        trapdoor = BlockWithItem.of(new TrapdoorBlock(blockSettingsSet.getTrapdoor()), block -> new BlockItem(block, itemSettingsSet.getTrapdoor()));
        door = BlockWithItem.of(new DoorBlock(blockSettingsSet.getDoor()), block -> new BlockItem(block, itemSettingsSet.getDoor()));
        var signTextureId = idFor("entity/sign/%s");
        Block[] temp = new Block[1];
        BiFunction<Block, Block, Item> signItem = (block, block2) -> new SignItem(itemSettingsSet.getSign(), block, block2);
        new Function<Supplier<Block>, Supplier<Item>>() {

            Block floor;
            Block wall;
            Item toBe;

            @Override
            public Supplier<Item> apply(Supplier<Block> blockSupplier) {
                if (floor == null) {
                    floor = blockSupplier.get();
                } else if (wall == null) {
                    wall = blockSupplier.get();
                } else if (toBe == null) {
                    toBe = signItem.apply()
                }
                return () -> toBe;
            }
        }
        floorSign = BlockWithItem.of(() -> new TerraformSignBlock(signTextureId, blockSettingsSet.getFloorSign()), );
        wallSign = BlockWithItem.of(() -> new TerraformWallSignBlock(signTextureId, blockSettingsSet.getWallSign()), itemFunction);
    }

    public static WoodSuite of(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
                               @NotNull WoodSuite.ItemSettingsSet itemSettingsSet, @NotNull Registry<Block> blockRegistry,
                               @NotNull Registry<Item> itemRegistry, @NotNull MapColor logColor, @NotNull BlockSoundGroup leavesSound, @NotNull SaplingGenerator saplingGenerator) {
        return new WoodSuite(identifier, blockSettingsSet, itemSettingsSet, blockRegistry, itemRegistry, logColor, leavesSound, saplingGenerator);
    }

    public void register() {
        Registry.register(blockRegistry, idFor("stripped_%s_log"), strippedLog.block());
        Registry.register(blockRegistry, idFor("stripped_%s_wood"), strippedWood.block());
        Registry.register(blockRegistry, idFor("%s_log"), log.block());
        Registry.register(blockRegistry, idFor("%s_wood"), wood.block());
        Registry.register(blockRegistry, idFor("%s_leaves"), leaves.block());
        Registry.register(blockRegistry, idFor("%s_sapling"), sapling.block());
        Registry.register(blockRegistry, idFor("potted_%s_sapling"), pottedSapling.block());
        Registry.register(blockRegistry, idFor("%s_planks"), planks.block());
        Registry.register(blockRegistry, idFor("%s_stairs"), stairs.block());
        Registry.register(blockRegistry, idFor("%s_slab"), slab.block());
        Registry.register(blockRegistry, idFor("%s_fence"), fence.block());
        Registry.register(blockRegistry, idFor("%s_fence_gate"), fenceGate.block());
        Registry.register(blockRegistry, idFor("%s_pressure_plate"), pressurePlate.block());
        Registry.register(blockRegistry, idFor("%s_button"), button.block());
        Registry.register(blockRegistry, idFor("%s_trapdoor"), trapdoor.block());
        Registry.register(blockRegistry, idFor("%s_door"), door.block());
        Registry.register(blockRegistry, idFor("%s_sign"), floorSign.block());
        Registry.register(blockRegistry, idFor("%s_wall_sign"), wallSign.block());

        Registry.register(itemRegistry, idFor("stripped_%s_log"), strippedLog.item());
        Registry.register(itemRegistry, idFor("stripped_%s_wood"), strippedWood.item());
        Registry.register(itemRegistry, idFor("%s_log"), log.item());
        Registry.register(itemRegistry, idFor("%s_wood"), wood.item());
        Registry.register(itemRegistry, idFor("%s_leaves"), leaves.item());
        Registry.register(itemRegistry, idFor("%s_sapling"), sapling.item());
        Registry.register(itemRegistry, idFor("potted_%s_sapling"), pottedSapling.item());
        Registry.register(itemRegistry, idFor("%s_planks"), planks.item());
        Registry.register(itemRegistry, idFor("%s_stairs"), stairs.item());
        Registry.register(itemRegistry, idFor("%s_slab"), slab.item());
        Registry.register(itemRegistry, idFor("%s_fence"), fence.item());
        Registry.register(itemRegistry, idFor("%s_fence_gate"), fenceGate.item());
        Registry.register(itemRegistry, idFor("%s_pressure_plate"), pressurePlate.item());
        Registry.register(itemRegistry, idFor("%s_button"), button.item());
        Registry.register(itemRegistry, idFor("%s_trapdoor"), trapdoor.item());
        Registry.register(itemRegistry, idFor("%s_door"), door.item());
        Registry.register(itemRegistry, idFor("%s_sign"), floorSign.item());
        Registry.register(itemRegistry, idFor("%s_wall_sign"), wallSign.item());
    }

    public BlockSettingsSet getBlockSettingsSet() {
        return blockSettingsSet;
    }

    protected final Identifier idFor(String replacer) {
        return idFor(id, replacer);
    }

    protected final Identifier idFor(Identifier id, String replacer) {
        return new Identifier(id.getNamespace(), replacer.formatted(id.getPath()));
    }

    public static class BlockWithItem {

        final Supplier<Block> block;
        final Supplier<Item> item;

        BlockWithItem(Supplier<Block> block, Supplier<Item> item) {
            this.block = block;
            this.item = item;
        }

        public Block block() {
            return block.get();
        }

        public Item item() {
            return item.get();
        }

        public static BlockWithItem of(Supplier<Block> block, Function<Supplier<Block>, Supplier<Item>> blockItemFunction) {
            return new BlockWithItem(block, blockItemFunction.apply(block));
        }
    }

    public static record CopySettingsSet(Block log, Block wood, Block strippedLog, Block strippedWood, Block leaves,
                                         Block sapling, Block pottedSapling, Block planks, Block stairs, Block slab,
                                         Block fence, Block fenceGate, Block pressurePlate, Block button,
                                         Block trapdoor, Block door, Block floorSign, Block wallSign) implements BlockSettingsSet {

        public static final CopySettingsSet DEFAULT_SETTINGS_SET = new CopySettingsSet(Blocks.OAK_LOG, Blocks.OAK_WOOD,
                Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_OAK_WOOD, Blocks.OAK_LEAVES, Blocks.OAK_SAPLING,
                Blocks.POTTED_OAK_SAPLING, Blocks.OAK_PLANKS, Blocks.OAK_STAIRS, Blocks.OAK_SLAB, Blocks.OAK_FENCE,
                Blocks.OAK_FENCE_GATE, Blocks.OAK_PRESSURE_PLATE, Blocks.OAK_BUTTON, Blocks.OAK_TRAPDOOR, Blocks.OAK_DOOR,
                Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN);

        @Override
        public AbstractBlock.Settings getLog() {
            return FabricBlockSettings.copyOf(log);
        }

        @Override
        public AbstractBlock.Settings getWood() {
            return FabricBlockSettings.copyOf(wood);
        }

        @Override
        public AbstractBlock.Settings getStrippedLog() {
            return FabricBlockSettings.copyOf(strippedLog);
        }

        @Override
        public AbstractBlock.Settings getStrippedWood() {
            return FabricBlockSettings.copyOf(strippedWood);
        }

        @Override
        public AbstractBlock.Settings getLeaves() {
            return FabricBlockSettings.copyOf(leaves);
        }

        @Override
        public AbstractBlock.Settings getSapling() {
            return FabricBlockSettings.copyOf(sapling);
        }

        @Override
        public AbstractBlock.Settings getPottedSapling() {
            return FabricBlockSettings.copyOf(pottedSapling);
        }

        @Override
        public AbstractBlock.Settings getPlanks() {
            return FabricBlockSettings.copyOf(planks);
        }

        @Override
        public AbstractBlock.Settings getStairs() {
            return FabricBlockSettings.copyOf(stairs);
        }

        @Override
        public AbstractBlock.Settings getSlab() {
            return FabricBlockSettings.copyOf(slab);
        }

        @Override
        public AbstractBlock.Settings getFence() {
            return FabricBlockSettings.copyOf(fence);
        }

        @Override
        public AbstractBlock.Settings getFenceGate() {
            return FabricBlockSettings.copyOf(fenceGate);
        }

        @Override
        public AbstractBlock.Settings getPressurePlate() {
            return FabricBlockSettings.copyOf(pressurePlate);
        }

        @Override
        public AbstractBlock.Settings getButton() {
            return FabricBlockSettings.copyOf(button);
        }

        @Override
        public AbstractBlock.Settings getTrapdoor() {
            return FabricBlockSettings.copyOf(trapdoor);
        }

        @Override
        public AbstractBlock.Settings getDoor() {
            return FabricBlockSettings.copyOf(door);
        }

        @Override
        public AbstractBlock.Settings getFloorSign() {
            return FabricBlockSettings.copyOf(floorSign);
        }

        @Override
        public AbstractBlock.Settings getWallSign() {
            return FabricBlockSettings.copyOf(wallSign);
        }
    }

    public interface BlockSettingsSet {
        AbstractBlock.Settings getLog();
        AbstractBlock.Settings getWood();
        AbstractBlock.Settings getStrippedLog();
        AbstractBlock.Settings getStrippedWood();
        AbstractBlock.Settings getLeaves();
        AbstractBlock.Settings getSapling();
        AbstractBlock.Settings getPottedSapling();
        AbstractBlock.Settings getPlanks();
        AbstractBlock.Settings getStairs();
        AbstractBlock.Settings getSlab();
        AbstractBlock.Settings getFence();
        AbstractBlock.Settings getFenceGate();
        AbstractBlock.Settings getPressurePlate();
        AbstractBlock.Settings getButton();
        AbstractBlock.Settings getTrapdoor();
        AbstractBlock.Settings getDoor();
        AbstractBlock.Settings getFloorSign();
        AbstractBlock.Settings getWallSign();
    }

    public static class GroupedSettings implements ItemSettingsSet {

        final ItemGroup group;

        GroupedSettings(ItemGroup group) {
            this.group = group;
        }

        private Item.Settings settings() {
            return new FabricItemSettings().group(group);
        }

        @Override
        public Item.Settings getLog() {
            return settings();
        }

        @Override
        public Item.Settings getWood() {
            return settings();
        }

        @Override
        public Item.Settings getStrippedLog() {
            return settings();
        }

        @Override
        public Item.Settings getStrippedWood() {
            return settings();
        }

        @Override
        public Item.Settings getLeaves() {
            return settings();
        }

        @Override
        public Item.Settings getSapling() {
            return settings();
        }

        @Override
        public Item.Settings getPottedSapling() {
            return settings();
        }

        @Override
        public Item.Settings getPlanks() {
            return settings();
        }

        @Override
        public Item.Settings getStairs() {
            return settings();
        }

        @Override
        public Item.Settings getSlab() {
            return settings();
        }

        @Override
        public Item.Settings getFence() {
            return settings();
        }

        @Override
        public Item.Settings getFenceGate() {
            return settings();
        }

        @Override
        public Item.Settings getPressurePlate() {
            return settings();
        }

        @Override
        public Item.Settings getButton() {
            return settings();
        }

        @Override
        public Item.Settings getTrapdoor() {
            return settings();
        }

        @Override
        public Item.Settings getDoor() {
            return settings();
        }

        @Override
        public Item.Settings getSign() {
            return settings().maxCount(16);
        }
    }

    public interface ItemSettingsSet {
        Item.Settings getLog();
        Item.Settings getWood();
        Item.Settings getStrippedLog();
        Item.Settings getStrippedWood();
        Item.Settings getLeaves();
        Item.Settings getSapling();
        Item.Settings getPottedSapling();
        Item.Settings getPlanks();
        Item.Settings getStairs();
        Item.Settings getSlab();
        Item.Settings getFence();
        Item.Settings getFenceGate();
        Item.Settings getPressurePlate();
        Item.Settings getButton();
        Item.Settings getTrapdoor();
        Item.Settings getDoor();
        Item.Settings getSign();
    }
}

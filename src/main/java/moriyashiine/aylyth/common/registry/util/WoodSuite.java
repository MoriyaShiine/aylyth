package moriyashiine.aylyth.common.registry.util;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

// TODO remove because of WoodType and BlockSetType
@SuppressWarnings("removal")
public class WoodSuite {

    private final Identifier id;
    private final BlockSettingsSet blockSettingsSet;
    private final Registry<Block> blockRegistry;
    public final Block strippedLog;
    public final Block strippedWood;
    public final Block log;
    public final Block wood;
    public final Block sapling;
    public final Block pottedSapling;
    public final Block planks;
    public final Block stairs;
    public final Block slab;
    public final Block fence;
    public final Block fenceGate;
    public final Block pressurePlate;
    public final Block button;
    public final Block trapdoor;
    public final Block door;
    public final TerraformSignBlock floorSign;
    public final Block wallSign;

    WoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
              @NotNull Registry<Block> blockRegistry, @NotNull MapColor logColor,
              SaplingGenerator saplingGenerator, Block strippedLog, Block strippedWood,
              Block log, Block wood, Block sapling,
              Block pottedSapling, Block planks, Block stairs, Block slab,
              Block fence, Block fenceGate, Block pressurePlate, Block button,
              Block trapdoor, Block door, TerraformSignBlock floorSign, Block wallSign,
              WoodType woodType) {
        id = identifier;
        this.blockSettingsSet = blockSettingsSet;
        this.blockRegistry = blockRegistry;
        this.strippedLog = strippedLog == null ? new PillarBlock(blockSettingsSet.getStrippedLog()) : strippedLog;
        this.strippedWood = strippedWood == null ? new PillarBlock(blockSettingsSet.getStrippedWood()) : strippedWood;
        this.log = log == null ? new StrippableLogBlock(() -> this.strippedLog, logColor, blockSettingsSet.getLog()) : log;
        this.wood = wood == null ? new StrippableLogBlock(() -> this.strippedWood, logColor, blockSettingsSet.getWood()) : wood;
        this.sapling = sapling == null ? new SaplingBlock(saplingGenerator, blockSettingsSet.getSapling()) : sapling;
        this.pottedSapling = pottedSapling == null ? new FlowerPotBlock(sapling, blockSettingsSet.getPottedSapling()) : pottedSapling;
        this.planks = planks == null ? new Block(blockSettingsSet.getPlanks()) : planks;
        this.stairs = stairs == null ? new StairsBlock(this.planks.getDefaultState(), blockSettingsSet.getStairs()) : stairs;
        this.slab = slab == null ? new SlabBlock(blockSettingsSet.getSlab()) : slab;
        this.fence = fence == null ? new FenceBlock(blockSettingsSet.getFence()) : fence;
        this.fenceGate = fenceGate == null ? new FenceGateBlock(blockSettingsSet.getFenceGate(), woodType) : fenceGate;
        this.pressurePlate = pressurePlate == null ? new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, blockSettingsSet.getPressurePlate(), woodType.setType()) : pressurePlate;
        this.button = button == null ? new ButtonBlock(blockSettingsSet.getButton(), woodType.setType(), 30, true) : button;
        this.trapdoor = trapdoor == null ? new TrapdoorBlock(blockSettingsSet.getTrapdoor(), woodType.setType()) : trapdoor;
        this.door = door == null ? new DoorBlock(blockSettingsSet.getDoor(), woodType.setType()) : door;
        Identifier signTextureId = idFor("entity/sign/%s");
        this.floorSign = floorSign == null ? new TerraformSignBlock(signTextureId, blockSettingsSet.getFloorSign()) : floorSign;
        this.wallSign = wallSign == null ? new TerraformWallSignBlock(signTextureId, blockSettingsSet.getWallSign()) : wallSign;
    }

    WoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
              @NotNull Registry<Block> blockRegistry, @NotNull MapColor logColor, @NotNull SaplingGenerator saplingGenerator,
              WoodType woodType) {
        id = identifier;
        this.blockSettingsSet = blockSettingsSet;
        this.blockRegistry = blockRegistry;
        strippedLog = new PillarBlock(blockSettingsSet.getStrippedLog());
        strippedWood = new PillarBlock(blockSettingsSet.getStrippedWood());
        log = new StrippableLogBlock(() -> strippedLog, logColor, blockSettingsSet.getLog());
        wood = new StrippableLogBlock(() -> strippedWood, logColor, blockSettingsSet.getWood());
        sapling = new SaplingBlock(saplingGenerator, blockSettingsSet.getSapling());
        pottedSapling = new FlowerPotBlock(sapling, blockSettingsSet.getPottedSapling());
        planks = new Block(blockSettingsSet.getPlanks());
        stairs = new StairsBlock(planks.getDefaultState(), blockSettingsSet.getStairs());
        slab = new SlabBlock(blockSettingsSet.getSlab());
        fence = new FenceBlock(blockSettingsSet.getFence());
        fenceGate = new FenceGateBlock(blockSettingsSet.getFenceGate(), woodType);
        pressurePlate = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, blockSettingsSet.getPressurePlate(), woodType.setType());
        button = new ButtonBlock(blockSettingsSet.getButton(), woodType.setType(), 30, true);
        trapdoor = new TrapdoorBlock(blockSettingsSet.getTrapdoor(), woodType.setType());
        door = new DoorBlock(blockSettingsSet.getDoor(), woodType.setType());
        Identifier signTextureId = idFor("entity/sign/%s");
        floorSign = new TerraformSignBlock(signTextureId, blockSettingsSet.getFloorSign());
        wallSign = new TerraformWallSignBlock(signTextureId, blockSettingsSet.getWallSign());
    }

    public static WoodSuite of(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
                               @NotNull Registry<Block> blockRegistry, @NotNull MapColor logColor,
                               @NotNull SaplingGenerator saplingGenerator, WoodType woodType) {
        return new WoodSuite(identifier, blockSettingsSet, blockRegistry, logColor, saplingGenerator, woodType);
    }

    public static WoodSuite of(@NotNull Identifier identifier, @NotNull WoodSuite.BlockSettingsSet blockSettingsSet,
                               @NotNull Registry<Block> blockRegistry, @NotNull MapColor logColor,
                               SaplingGenerator saplingGenerator, Block strippedLog, Block strippedWood,
                               Block log, Block wood, Block sapling,
                               Block pottedSapling, Block planks, Block stairs, Block slab,
                               Block fence, Block fenceGate, Block pressurePlate, Block button,
                               Block trapdoor, Block door, TerraformSignBlock floorSign, Block wallSign,
                               WoodType woodType) {
        return new WoodSuite(identifier, blockSettingsSet, blockRegistry, logColor, saplingGenerator, strippedLog,
                strippedWood, log, wood, sapling, pottedSapling, planks, stairs, slab, fence, fenceGate, pressurePlate,
                button, trapdoor, door, floorSign, wallSign, woodType);
    }

    public void register() {
        Registry.register(blockRegistry, idFor("stripped_%s_log"), strippedLog);
        Registry.register(blockRegistry, idFor("stripped_%s_wood"), strippedWood);
        Registry.register(blockRegistry, idFor("%s_log"), log);
        Registry.register(blockRegistry, idFor("%s_wood"), wood);
        Registry.register(blockRegistry, idFor("%s_sapling"), sapling);
        Registry.register(blockRegistry, idFor("potted_%s_sapling"), pottedSapling);
        Registry.register(blockRegistry, idFor("%s_planks"), planks);
        Registry.register(blockRegistry, idFor("%s_stairs"), stairs);
        Registry.register(blockRegistry, idFor("%s_slab"), slab);
        Registry.register(blockRegistry, idFor("%s_fence"), fence);
        Registry.register(blockRegistry, idFor("%s_fence_gate"), fenceGate);
        Registry.register(blockRegistry, idFor("%s_pressure_plate"), pressurePlate);
        Registry.register(blockRegistry, idFor("%s_button"), button);
        Registry.register(blockRegistry, idFor("%s_trapdoor"), trapdoor);
        Registry.register(blockRegistry, idFor("%s_door"), door);
        Registry.register(blockRegistry, idFor("%s_sign"), floorSign);
        Registry.register(blockRegistry, idFor("%s_wall_sign"), wallSign);
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

    class Builder {

    }

    public record CopySettingsSet(Block log, Block wood, Block strippedLog, Block strippedWood, Block sapling,
                                         Block pottedSapling, Block planks, Block stairs, Block slab, Block fence,
                                         Block fenceGate, Block pressurePlate, Block button, Block trapdoor,
                                         Block door, Block floorSign, Block wallSign) implements BlockSettingsSet {

        public static final CopySettingsSet DEFAULT_SETTINGS_SET = new CopySettingsSet(Blocks.OAK_LOG, Blocks.OAK_WOOD,
                Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_OAK_WOOD, Blocks.OAK_SAPLING,
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
}
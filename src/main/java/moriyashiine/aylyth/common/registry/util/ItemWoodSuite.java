package moriyashiine.aylyth.common.registry.util;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ItemWoodSuite {

    private final Identifier id;
    private final WoodSuite woodSuite;
    private final ItemSettingsSet itemSettings;
    private final Registry<Item> registry;
    public final Item strippedLog;
    public final Item strippedWood;
    public final Item log;
    public final Item wood;
    public final Item sapling;
    public final Item planks;
    public final Item stairs;
    public final Item slab;
    public final Item fence;
    public final Item fenceGate;
    public final Item pressurePlate;
    public final Item button;
    public final Item trapdoor;
    public final Item door;
    public final Item sign;
    public final Item boat;
    public final Item chestBoat;
    public final ItemGroup.Builder groupBuilder;

    ItemWoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite woodSuite,
                  @NotNull ItemSettingsSet itemSettingsSet, @NotNull Registry<Item> itemRegistry,
                  Item strippedLog, Item strippedWood, Item log, Item wood, Item sapling, Item pottedSapling,
                  Item planks, Item stairs, Item slab, Item fence, Item fenceGate, Item pressurePlate, Item button,
                  Item trapdoor, Item door, Item sign, Item boat, Item chestBoat, ItemGroup.Builder groupBuilder) {
        this.id = identifier;
        this.woodSuite = woodSuite;
        this.itemSettings = itemSettingsSet;
        this.registry = itemRegistry;
        this.strippedLog = strippedLog;
        this.strippedWood = strippedWood;
        this.log = log;
        this.wood = wood;
        this.sapling = sapling;
        this.planks = planks;
        this.stairs = stairs;
        this.slab = slab;
        this.fence = fence;
        this.fenceGate = fenceGate;
        this.pressurePlate = pressurePlate;
        this.button = button;
        this.trapdoor = trapdoor;
        this.door = door;
        this.sign = sign;
        this.boat = boat;
        this.chestBoat = chestBoat;
        this.groupBuilder = groupBuilder;
    }

    ItemWoodSuite(@NotNull Identifier identifier, @NotNull WoodSuite woodSuite,
                  @NotNull ItemSettingsSet itemSettingsSet, @NotNull Registry<Item> itemRegistry,
                  @NotNull RegistryKey<TerraformBoatType> boatType, ItemGroup.Builder groupBuilder) {
        this.id = identifier;
        this.woodSuite = woodSuite;
        this.itemSettings = itemSettingsSet;
        this.registry = itemRegistry;
        this.strippedLog = new BlockItem(woodSuite.strippedLog, itemSettings.getStrippedLog());
        this.strippedWood = new BlockItem(woodSuite.strippedWood, itemSettings.getStrippedWood());
        this.log = new BlockItem(woodSuite.log, itemSettings.getLog());
        this.wood = new BlockItem(woodSuite.wood, itemSettings.getWood());
        this.sapling = new BlockItem(woodSuite.sapling, itemSettings.getSapling());
        this.planks = new BlockItem(woodSuite.planks, itemSettings.getPlanks());
        this.stairs = new BlockItem(woodSuite.stairs, itemSettings.getStairs());
        this.slab = new BlockItem(woodSuite.slab, itemSettings.getSlab());
        this.fence = new BlockItem(woodSuite.fence, itemSettings.getFence());
        this.fenceGate = new BlockItem(woodSuite.fenceGate, itemSettings.getFenceGate());
        this.pressurePlate = new BlockItem(woodSuite.pressurePlate, itemSettings.getPressurePlate());
        this.button = new BlockItem(woodSuite.button, itemSettings.getButton());
        this.trapdoor = new BlockItem(woodSuite.trapdoor, itemSettings.getTrapdoor());
        this.door = new TallBlockItem(woodSuite.door, itemSettings.getDoor());
        this.sign = new SignItem(itemSettings.getSign(), woodSuite.floorSign, woodSuite.wallSign);
        this.boat = new TerraformBoatItem(boatType, false, itemSettings.getBoat());
        this.chestBoat = new TerraformBoatItem(boatType, true, itemSettings.getChestBoat());
        this.groupBuilder = groupBuilder;
    }

    public static ItemWoodSuite of(@NotNull Identifier identifier, @NotNull WoodSuite woodSuite,
                                   @NotNull ItemSettingsSet itemSettingsSet, @NotNull Registry<Item> itemRegistry,
                                   @NotNull RegistryKey<TerraformBoatType> boatType, ItemGroup.Builder groupBuilder) {
        return new ItemWoodSuite(identifier, woodSuite, itemSettingsSet, itemRegistry, boatType, groupBuilder);
    }
    
    public ObjectArraySet<Item> register() {
        var items = new ObjectArraySet<Item>(17);

        items.add(Registry.register(registry, idFor("stripped_%s_log"), strippedLog));
        items.add(Registry.register(registry, idFor("stripped_%s_wood"), strippedWood));
        items.add(Registry.register(registry, idFor("%s_log"), log));
        items.add(Registry.register(registry, idFor("%s_wood"), wood));
        items.add(Registry.register(registry, idFor("%s_sapling"), sapling));
        items.add(Registry.register(registry, idFor("%s_planks"), planks));
        items.add(Registry.register(registry, idFor("%s_stairs"), stairs));
        items.add(Registry.register(registry, idFor("%s_slab"), slab));
        items.add(Registry.register(registry, idFor("%s_fence"), fence));
        items.add(Registry.register(registry, idFor("%s_fence_gate"), fenceGate));
        items.add(Registry.register(registry, idFor("%s_pressure_plate"), pressurePlate));
        items.add(Registry.register(registry, idFor("%s_button"), button));
        items.add(Registry.register(registry, idFor("%s_trapdoor"), trapdoor));
        items.add(Registry.register(registry, idFor("%s_door"), door));
        items.add(Registry.register(registry, idFor("%s_sign"), sign));
        items.add(Registry.register(registry, idFor("%s_boat"), boat));
        items.add(Registry.register(registry, idFor("%s_chest_boat"), chestBoat));

        return items;
    }

    protected final Identifier idFor(String replacer) {
        return idFor(id, replacer);
    }

    protected final Identifier idFor(Identifier id, String replacer) {
        return new Identifier(id.getNamespace(), replacer.formatted(id.getPath()));
    }

    // TODO remove
    public static class GroupedSettings implements ItemSettingsSet {

        private Item.Settings settings() {
            return new FabricItemSettings();
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
        public Item.Settings getSapling() {
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

        @Override
        public Item.Settings getBoat() {
            return settings().maxCount(1);
        }

        @Override
        public Item.Settings getChestBoat() {
            return settings().maxCount(1);
        }
    }

    public interface ItemSettingsSet {
        Item.Settings getLog();
        Item.Settings getWood();
        Item.Settings getStrippedLog();
        Item.Settings getStrippedWood();
        Item.Settings getSapling();
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
        Item.Settings getBoat();
        Item.Settings getChestBoat();
    }
}

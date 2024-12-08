package co.johnrowley.gtceu_extra.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryInfo;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModLoader;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;
import static com.gregtechceu.gtceu.common.data.GTMachines.registerSimpleMachines;

public class GTCEuExtraMachines {
    public static final MachineDefinition[] DEHYDRATOR = registerSimpleMachines("dehydrator",
            GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static MachineDefinition[] THRESHER = registerSimpleMachines("thresher",
                                      GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static MachineDefinition[] FOOD_PROCESSOR = registerSimpleMachines("food_processor",
                                            GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static final MultiblockMachineDefinition STEAM_SQUASHER = GTCEU_EXTRA_REGISTRATE
            .multiblock("steam_squasher", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.COMPRESSOR_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", "XXX")
                    .aisle("XXX", "X X", "XXX")
                    .aisle("XXX", "X X", "XXX")
                    .aisle("XXX", "XSX", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where(' ', Predicates.air())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/compressor"))
            .register();

    public static final MultiblockMachineDefinition STEAM_WASHER = GTCEU_EXTRA_REGISTRATE
            .multiblock("steam_washer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.ORE_WASHER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXX    ", "XGGGX    ", "XGGGX    ", " XXX     ", "         ", "         ")
                    .aisle("XXXXX XXX", "GWWWG XXX", "G   G XXX", "X   X    ", "         ", "         ")
                    .aisle("XXXXX XXX", "GWPWG X X", "G P G XXX", "X P X  P ", "  P    P ", "  PPPPPP ")
                    .aisle("XXXXX XXX", "GWWWG XSX", "G   G XXX", "X   X    ", "         ", "         ")
                    .aisle("XXXXX    ", "XGGGX    ", "XGGGX    ", " XXX     ", "         ", "         ")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where(' ', Predicates.air())
                    .where('G', blocks(Blocks.GLASS))
                    .where('W', blocks(Blocks.WATER))
                    .where('P', blocks(CASING_BRONZE_PIPE.get()))
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/ore_washer"))
            .register();

    public static final MultiblockMachineDefinition STEAM_CENTRIFUGE = GTCEU_EXTRA_REGISTRATE
            .multiblock("steam_centrifuge", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" XXX ", " XXX ", "  X  ", "     ", " XXX ")
                    .aisle("XXXXX", "X   X", " XXX ", " XXX ", "XXXXX")
                    .aisle("XXXXX", "X   X", "XX XX", " X X ", "XXXXX")
                    .aisle("XXXXX", "X   X", " XXX ", " XXX ", "XXXXX")
                    .aisle(" XXX ", " XSX ", "  X  ", "     ", " XXX ")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where(' ', Predicates.air())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/centrifuge"))
            .register();

    public static final MultiblockMachineDefinition STEAM_FORGE_HAMMER = GTCEU_EXTRA_REGISTRATE
            .multiblock("steam_forge_hammer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" XXX ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .aisle("XXXXX", " XXX ", "     ", "     ", "     ", "     ", "     ")
                    .aisle("XXXXX", "XX XX", "X   X", "X I X", "X I X", "XXXXX", "  P  ")
                    .aisle("XXXXX", " XSX ", "     ", "     ", "     ", "     ", "     ")
                    .aisle(" XXX ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where(' ', Predicates.air())
                    .where('P', blocks(CASING_BRONZE_PIPE.get()))
                    .where('I', blocks(Blocks.IRON_BLOCK))
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/forge_hammer"))
            .register();

    public static final MultiblockMachineDefinition STEAM_MIXER = GTCEU_EXTRA_REGISTRATE
            .multiblock("steam_mixer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.MIXER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" XXXXX ", " XXXXX ", " XXXXX ", "   X   ", "   X   ", "       ")
                    .aisle("XXXXXXX", "XI   IX", "X     X", "       ", "   X   ", "   X   ")
                    .aisle("XXXXXXX", "X I I X", "X     X", "       ", "       ", "   X   ")
                    .aisle("XXXXXXX", "X  P  X", "X  P  X", "X  P  X", "XX P XX", " XXXXX ")
                    .aisle("XXXXXXX", "X I I X", "X     X", "       ", "       ", "   X   ")
                    .aisle("XXXXXXX", "XI   IX", "X     X", "       ", "   X   ", "   X   ")
                    .aisle(" XXXXX ", " XXSXX ", " XXXXX ", "   X   ", "   X   ", "       ")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('P', blocks(CASING_BRONZE_PIPE.get()))
                    .where('I', blocks(Blocks.IRON_BLOCK))
                    .where(' ', Predicates.air())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get())
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/mixer"))
            .register();

    public static void init(){
        if (GTCEu.isKubeJSLoaded()) {

        }
    }
}

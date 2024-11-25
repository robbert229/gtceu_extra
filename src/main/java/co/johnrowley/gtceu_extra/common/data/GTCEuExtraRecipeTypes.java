package co.johnrowley.gtceu_extra.common.data;

import com.gregtechceu.gtceu.api.registry.GTRegistries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.*;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.ModLoader;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MULTIBLOCK;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;


public class GTCEuExtraRecipeTypes {
    public static final String FISH_HATCHERY = "fish_hatchery";
    public static final String ALGAE_REACTOR = "algae_reactor";

    public static final String THRESHER = "thresher";
    public static final String FOOD_PROCESSOR = "food_processor";

    public final static GTRecipeType FISH_HATCHERY_RECIPES = register("fish_hatchery", MULTIBLOCK)
            .setMaxIOSize(6, 1, 2, 1).setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public final static GTRecipeType ALGAE_REACTOR_RECIPES = register("algae_reactor_recipes", MULTIBLOCK)
            .setMaxIOSize(6, 1, 2, 1).setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static GTRecipeType THRESHER_RECIPES = register("thresher", ELECTRIC)
            .setMaxIOSize(6, 1, 2, 1).setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static GTRecipeType FOOD_PROCESSOR_RECIPES = register("food_processor", ELECTRIC)
            .setMaxIOSize(6, 1, 2, 1).setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.DUST_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MOTOR);

    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(GTCEu.id(name), group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public static void init(){

    }
}

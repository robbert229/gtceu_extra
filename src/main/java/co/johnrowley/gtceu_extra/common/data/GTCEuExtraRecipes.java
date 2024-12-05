package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.data.recipe.*;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.function.Consumer;

public class GTCEuExtraRecipes {
    public static final Set<ResourceLocation> RECIPE_FILTERS = new ObjectOpenHashSet<>();

    public static void recipeAddition(Consumer<FinishedRecipe> originalConsumer) {
        Consumer<FinishedRecipe> consumer = recipe -> {
            if (!RECIPE_FILTERS.contains(recipe.getId())) {
                originalConsumer.accept(recipe);
            }
        };

        AlgaeReactorRecipes.init(consumer);
        FishHatcheryRecipes.init(consumer);
        FoodProcessorRecipes.init(consumer);
        ThresherRecipes.init(consumer);

        CraftingRecipeLoader.init(consumer);
        AssemblerRecipeLoader.init(consumer);
    }

    /*
     * Called on resource reload in-game, just before the above method.
     *
     * This is also where any recipe removals should happen.
     */
    public static void recipeRemoval(Consumer<ResourceLocation> consumer) {
        RECIPE_FILTERS.clear();
    }
}

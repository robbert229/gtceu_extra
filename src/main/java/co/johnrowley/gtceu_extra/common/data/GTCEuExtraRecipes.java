package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.data.recipe.AlgaeReactorRecipes;
import co.johnrowley.gtceu_extra.data.recipe.FishHatcheryRecipes;
import co.johnrowley.gtceu_extra.data.recipe.FoodProcessorRecipes;
import co.johnrowley.gtceu_extra.data.recipe.ThresherRecipes;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.function.Consumer;

public class GTCEuExtraRecipes {
    public static final Set<ResourceLocation> RECIPE_FILTERS = new ObjectOpenHashSet<>();

    /*
     * Called on resource reload in-game.
     *
     * These methods are meant for recipes that cannot be reasonably changed by a Datapack,
     * such as "X Ingot -> 2 X Rods" types of recipes, that follow a pattern for many recipes.
     *
     * This should also be used for recipes that need
     * to respond to a config option in ConfigHolder.
     */
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

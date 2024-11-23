package co.johnrowley.gtceu_extra.common.recipe.condition;

import co.johnrowley.gtceu_extra.common.data.GTCEuExtraRecipeConditions;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTrait;
import net.dries007.tfc.common.capabilities.food.IFood;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class GTCEuExtraTFCFoodTraitCondition extends RecipeCondition {
    private final Supplier<RecipeConditionType<?>> getTypeFn;
    private final Component tooltip;
    private final BiFunction<GTRecipe, RecipeLogic, Boolean> testerFn;

    public static RecipeConditionType.ConditionFactory<GTCEuExtraTFCFoodTraitCondition> create(
            Supplier<RecipeConditionType<?>> getTypeFn,
            Component tooltip,
            BiFunction<GTRecipe, RecipeLogic, Boolean> testerFn
    ) {
        return () -> new GTCEuExtraTFCFoodTraitCondition(getTypeFn, tooltip, testerFn);
    }

    public static BiFunction<GTRecipe, RecipeLogic, Boolean> testFoodTrait(Function<IFood, Boolean> tester) {
        return (gtRecipe, recipeLogic) -> gtRecipe.getIngredients().stream().allMatch(ingredient -> {
            return Arrays.stream(ingredient.getItems()).allMatch(stack -> {
                IFood food = FoodCapability.get(stack);
                return tester.apply(food);
            });
        });
    }

    private GTCEuExtraTFCFoodTraitCondition(
            Supplier<RecipeConditionType<?>> getTypeFn,
            Component tooltip,
            BiFunction<GTRecipe, RecipeLogic, Boolean> testerFn
    ) {
        this.getTypeFn = getTypeFn;
        this.tooltip = tooltip;
        this.testerFn = testerFn;
    }

    @Override
    public RecipeConditionType<?> getType() {
        return this.getTypeFn.get();
    }

    @Override
    public Component getTooltips() {
        return this.tooltip;
    }

    @Override
    public boolean test(@NotNull GTRecipe gtRecipe, @NotNull RecipeLogic recipeLogic) {
        return this.testerFn.apply(gtRecipe, recipeLogic);
    }

    @Override
    public RecipeCondition createTemplate() {
        return new GTCEuExtraTFCFoodTraitCondition(null, null, null);
    }
}

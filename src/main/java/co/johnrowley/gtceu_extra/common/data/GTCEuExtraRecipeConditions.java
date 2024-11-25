package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import co.johnrowley.gtceu_extra.common.recipe.condition.GTCEuExtraTFCFoodTraitCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class GTCEuExtraRecipeConditions {
    @Nullable
    public static RecipeConditionType<GTCEuExtraTFCFoodTraitCondition> TFC_ROTTEN_CONDITION;

    @Nullable
    public static RecipeConditionType<GTCEuExtraTFCFoodTraitCondition> TFC_NOT_ROTTEN_CONDITION;

    public static void init() {
        if (GTCEuExtra.isTFCLoaded()) {
            TFC_ROTTEN_CONDITION = GTRegistries.RECIPE_CONDITIONS.register(
                "tfc_rotten",
                new RecipeConditionType(
                    GTCEuExtraTFCFoodTraitCondition.create(() -> TFC_ROTTEN_CONDITION,
                        Component.translatable("gtceu_extra.condition.tfc_food_trait"),
                        GTCEuExtraTFCFoodTraitCondition.testFoodTrait(
                                (food) -> food.isRotten()
                        )
                    ), GTCEuExtraTFCFoodTraitCondition.CODEC
                )
            );

            TFC_NOT_ROTTEN_CONDITION = GTRegistries.RECIPE_CONDITIONS.register(
                    "tfc_not_rotten",
                    new RecipeConditionType(
                            GTCEuExtraTFCFoodTraitCondition.create(() -> TFC_NOT_ROTTEN_CONDITION,
                                    Component.translatable("gtceu_extra.condition.tfc_food_trait"),
                                    GTCEuExtraTFCFoodTraitCondition.testFoodTrait(
                                            (food) -> !food.isRotten()
                                    )
                            ), GTCEuExtraTFCFoodTraitCondition.CODEC
                    )
            );
        }
    }
}

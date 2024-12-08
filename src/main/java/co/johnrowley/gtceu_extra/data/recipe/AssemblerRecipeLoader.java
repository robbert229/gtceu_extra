package co.johnrowley.gtceu_extra.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static co.johnrowley.gtceu_extra.common.data.GTCEuExtraItems.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;

public class AssemblerRecipeLoader {
    public static void init(Consumer<FinishedRecipe> consumer) {
        buildBatteryManagementCover(GTValues.ULV)
            .inputItems(plate, WroughtIron, 1)
            .inputItems(Items.PISTON, 1)
            .outputItems(BATTERY_MANAGEMENT_COVER_ULV.asStack())
            .save(consumer);

        buildBatteryManagementCover(GTValues.LV)
            .inputItems(plate, Steel, 1)
            .inputItems(GTItems.ELECTRIC_PISTON_LV, 1)
            .outputItems(BATTERY_MANAGEMENT_COVER_LV.asStack())
            .save(consumer);

        buildBatteryManagementCover(GTValues.MV)
            .inputItems(plate, Aluminium, 1)
            .inputItems(GTItems.ELECTRIC_PISTON_MV, 1)
            .outputItems(BATTERY_MANAGEMENT_COVER_MV.asStack())
            .save(consumer);

        buildBatteryManagementCover(GTValues.HV)
            .inputItems(plate, StainlessSteel, 1)
            .inputItems(GTItems.ELECTRIC_PISTON_HV, 1)
            .outputItems(BATTERY_MANAGEMENT_COVER_HV.asStack())
            .save(consumer);
    }

    private static GTRecipeBuilder buildBatteryManagementCover(int tier) {
        var recipeId = String.format("battery_management_cover_%s", GTValues.VN[tier].toLowerCase());
        return ASSEMBLER_RECIPES.recipeBuilder(recipeId)
            .circuitMeta(9)
            .EUt(GTValues.VH[tier])
            .duration(50)
            .inputItems(Items.COMPARATOR, 1)
            .inputItems(GTItems.ITEM_FILTER, 1);
    }
}

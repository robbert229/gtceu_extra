package co.johnrowley.gtceu_extra;

import co.johnrowley.gtceu_extra.common.data.GTCEuExtraRecipes;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class GTCEuExtraAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return null;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GTCEuExtra.MOD_ID;
    }

    @Override
    public void registerRecipeCapabilities() {
        IGTAddon.super.registerRecipeCapabilities();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GTCEuExtraRecipes.recipeAddition(provider);
    }

    @Override
    public void removeRecipes(Consumer<ResourceLocation> consumer) {
        GTCEuExtraRecipes.recipeRemoval(consumer);
    }
}

package co.johnrowley.gtceu_extra;

import co.johnrowley.gtceu_extra.common.data.*;
import co.johnrowley.gtceu_extra.data.recipe.GTCEuExtraTags;
import co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@GTAddon
public class GTCEuExtraAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        GTCEuExtraBlocks.init();
        GTCEuExtraItems.init();
        GTCEuExtraCreativeModeTabs.init();
    }

    @Override
    public void registerTagPrefixes(){
        GTCEuExtraTags.init();
    }

    @Override
    public void registerCovers() {
        IGTAddon.super.registerCovers();

        GTCEuExtraGTCovers.init();
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

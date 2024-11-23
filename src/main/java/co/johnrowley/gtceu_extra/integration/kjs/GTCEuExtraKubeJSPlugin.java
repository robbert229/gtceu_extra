package co.johnrowley.gtceu_extra.integration.kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Map;

public class GTCEuExtraKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void initStartup() {
        super.initStartup();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void registerEvents() {
        super.registerEvents();

    }

    @Override
    public void registerClasses(ScriptType type, ClassFilter filter) {
        super.registerClasses(type, filter);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        super.registerRecipeSchemas(event);

    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
        super.registerRecipeComponents(event);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);

    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        super.registerTypeWrappers(type, typeWrappers);
    }

    @Override
    public void injectRuntimeRecipes(RecipesEventJS event, RecipeManager manager,
                                     Map<ResourceLocation, Recipe<?>> recipesByName) {
        super.injectRuntimeRecipes(event,manager, recipesByName);
    }
}

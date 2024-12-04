package co.johnrowley.gtceu_extra;

import co.johnrowley.gtceu_extra.common.data.*;
import co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.LDLib;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GTCEuExtra.MOD_ID)
public class GTCEuExtra {
    public static final String MOD_ID = "gtceu_extra";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static MaterialRegistry MATERIAL_REGISTRY;

    public static boolean isTFCLoaded() {
        return LDLib.isModLoaded("terrafirmacraft");
    }

    public static boolean isIntegratedDynamicsLoaded() {
        return LDLib.isModLoaded("integrateddynamics");
    }

    public static void init(){
        GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE.registerRegistrate();
    }

    public GTCEuExtra() {
        GTCEuExtra.init();

        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);

        bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        bus.addGenericListener(RecipeConditionType.class, this::registerRecipeConditions);
        bus.addGenericListener(MachineDefinition.class, this::registerMachines);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * id is a helper utility that returns a resource location for a given path.
     */
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnder(path));
    }

    @SubscribeEvent
    public void registerMaterialRegistry(MaterialRegistryEvent event) {
        MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GTCEuExtra.MOD_ID);
    }

    @SubscribeEvent
    public void registerMaterials(MaterialEvent event) {
        GTCEuExtraMaterials.init();
    }

    public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        GTCEuExtraRecipeTypes.init();
    }

    public void registerRecipeConditions(GTCEuAPI.RegisterEvent<String, RecipeConditionType<?>> event) {
        GTCEuExtraRecipeConditions.init();
    }

    public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        GTCEuExtraMachines.init();
    }
}

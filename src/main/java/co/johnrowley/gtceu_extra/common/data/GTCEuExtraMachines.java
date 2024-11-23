package co.johnrowley.gtceu_extra.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryInfo;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModLoader;

import static com.gregtechceu.gtceu.common.data.GTMachines.registerSimpleMachines;

public class GTCEuExtraMachines {
    public static final MachineDefinition[] DEHYDRATOR = registerSimpleMachines("dehydrator",
            GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static MachineDefinition[] THRESHER = registerSimpleMachines("thresher",
                                      GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static MachineDefinition[] FOOD_PROCESSOR = registerSimpleMachines("food_processor",
                                            GTRecipeTypes.CHEMICAL_RECIPES, tier -> 16 * FluidType.BUCKET_VOLUME);

    public static void init(){
        if (GTCEu.isKubeJSLoaded()) {
            GTRegistryInfo.registerFor(GTRegistries.MACHINES.getRegistryName());
        }

        ModLoader.get().postEvent(new GTCEuAPI.RegisterEvent<>(GTRegistries.MACHINES, MachineDefinition.class));
        GTRegistries.MACHINES.freeze();
    }
}

package co.johnrowley.gtceu_extra.registry;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.dries007.tfc.common.capabilities.food.FoodTrait;
import net.minecraft.resources.ResourceLocation;

public class GTCEuExtraRegistry {
    public static GTRegistrate GTCEU_EXTRA_REGISTRATE = GTRegistrate.create(GTCEuExtra.MOD_ID);

    public static final FoodTrait RadiationSterilized = FoodTrait.register(
            new ResourceLocation(GTCEuExtra.MOD_ID,"radiation_sterilized"),
            new FoodTrait(0.01F,"gtceu_extra.radiation_sterilized"));
}

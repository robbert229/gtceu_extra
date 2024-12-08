package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;

public class GTCEuExtraCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GTCEU_EXTRA = GTCEU_EXTRA_REGISTRATE.defaultCreativeTab(
                    GTCEuExtra.MOD_ID,
                    builder -> builder
                            .title(Component.literal("GregTechCEu Extra Unofficial"))
                            .icon(GTCEuExtraItems.RED_CELLULOSE::asStack)
                            .displayItems((arg, output) -> {
                                var generator = new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GTCEuExtra.MOD_ID, GTCEU_EXTRA_REGISTRATE);

                                generator.accept(arg,output);

                                output.accept(GTCEuExtraMachines.STEAM_CENTRIFUGE.asStack());
                                output.accept(GTCEuExtraMachines.STEAM_FORGE_HAMMER.asStack());
                                output.accept(GTCEuExtraMachines.STEAM_MIXER.asStack());
                                output.accept(GTCEuExtraMachines.STEAM_SQUASHER.asStack());
                                output.accept(GTCEuExtraMachines.STEAM_WASHER.asStack());
                            })
                            .build())
            .register();


    public static void init() {

    }
}

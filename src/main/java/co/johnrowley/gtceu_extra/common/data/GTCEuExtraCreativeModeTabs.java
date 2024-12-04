package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;

public class GTCEuExtraCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GTCEU_EXTRA = GTCEU_EXTRA_REGISTRATE.defaultCreativeTab(GTCEuExtra.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GTCEuExtra.MOD_ID, GTCEU_EXTRA_REGISTRATE))
                            .icon(GTCEuExtraItems.RED_CELLULOSE::asStack)
                            .title(Component.literal("GregTechCEu Extra Unofficial"))
                            .build())
            .register();

    public static void init() {

    }
}

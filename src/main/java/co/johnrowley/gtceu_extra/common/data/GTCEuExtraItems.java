package co.johnrowley.gtceu_extra.common.data;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.commands.arguments.ResourceKeyArgument;

import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;
import static com.gregtechceu.gtceu.common.data.GTItems.attach;

@SuppressWarnings({"Convert2MethodRef", "unused"})
public class GTCEuExtraItems {
    // region algae
    public static final ItemEntry<ComponentItem> BROWN_ALGAE = GTCEU_EXTRA_REGISTRATE.item("brown_algae_item", ComponentItem::create)
            .lang("Brown Algae")
            .properties(p -> p.stacksTo(16))
            //.onRegister(attach(new GpsTrackerBehaviour()))
            .tab(GTCEuExtraCreativeModeTabs.GTCEU_EXTRA.getKey(), creativeModeTabModifier -> {
                //
            })
            .defaultModel()
            .register();
    // endregion

    // region food processor

    // endregion

    public static <T extends ComponentItem> NonNullConsumer<T> attach(IItemComponent... components) {
        return item -> item.attachComponents(components);
    }

    public static void init(){

    }
}

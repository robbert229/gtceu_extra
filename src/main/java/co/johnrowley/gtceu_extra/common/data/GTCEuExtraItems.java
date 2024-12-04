package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.data.recipe.GTCEuExtraTags;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.world.item.Item;

import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;

@SuppressWarnings({"Convert2MethodRef", "unused"})
public class GTCEuExtraItems {
    // region algae
    public static final ItemEntry<Item> BROWN_ALGAE = GTCEU_EXTRA_REGISTRATE.item("brown_algae", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.ALGAE, GTCEuExtraTags.BROWN_ALGAE)
            .register();

    public static final ItemEntry<Item> RED_ALGAE = GTCEU_EXTRA_REGISTRATE.item("red_algae", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.ALGAE, GTCEuExtraTags.RED_ALGAE)
            .register();

    public static final ItemEntry<Item> GOLDEN_BROWN_ALGAE = GTCEU_EXTRA_REGISTRATE.item("golden_brown_algae", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.ALGAE, GTCEuExtraTags.GOLDEN_BROWN_ALGAE)
            .register();

    public static final ItemEntry<Item> GREEN_ALGAE = GTCEU_EXTRA_REGISTRATE.item("green_algae", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.ALGAE, GTCEuExtraTags.GREEN_ALGAE)
            .register();

    public static final ItemEntry<Item> GREEN_CELLULOSE = GTCEU_EXTRA_REGISTRATE.item("green_cellulose", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.CELLULOSE, GTCEuExtraTags.GREEN_CELLULOSE)
            .register();

    public static final ItemEntry<Item> RED_CELLULOSE = GTCEU_EXTRA_REGISTRATE.item("red_cellulose", Item::new)
            .properties(p -> p.stacksTo(64))
            .defaultModel()
            .tag(GTCEuExtraTags.CELLULOSE, GTCEuExtraTags.RED_CELLULOSE)
            .register();

    public static final ItemEntry<Item> GOLDEN_BROWN_CELLULOSE = GTCEU_EXTRA_REGISTRATE.item("golden_brown_cellulose", Item::new)
            .defaultModel()
            .tag(GTCEuExtraTags.CELLULOSE, GTCEuExtraTags.GOLDEN_BROWN_CELLULOSE)
            .register();

    public static final ItemEntry<ComponentItem> BATTERY_MANAGEMENT_COVER_ULV = GTCEU_EXTRA_REGISTRATE
            .item("ulv_battery_management_cover", ComponentItem::create)
            .defaultModel()
            .onRegister(attach(new CoverPlaceBehavior(GTCEuExtraGTCovers.BATTERY_MANAGEMENT[0])))
            .register();

    public static final ItemEntry<ComponentItem> BATTERY_MANAGEMENT_COVER_LV = GTCEU_EXTRA_REGISTRATE
            .item("lv_battery_management_cover", ComponentItem::create)
            .defaultModel()
            .onRegister(attach(new CoverPlaceBehavior(GTCEuExtraGTCovers.BATTERY_MANAGEMENT[1])))
            .register();

    public static final ItemEntry<ComponentItem> BATTERY_MANAGEMENT_COVER_MV = GTCEU_EXTRA_REGISTRATE
            .item("mv_battery_management_cover", ComponentItem::create)
            .defaultModel()
            .onRegister(attach(new CoverPlaceBehavior(GTCEuExtraGTCovers.BATTERY_MANAGEMENT[2])))
            .register();

    public static final ItemEntry<ComponentItem> BATTERY_MANAGEMENT_COVER_HV = GTCEU_EXTRA_REGISTRATE
            .item("hv_battery_management_cover", ComponentItem::create)
            .defaultModel()
            .onRegister(attach(new CoverPlaceBehavior(GTCEuExtraGTCovers.BATTERY_MANAGEMENT[3])))
            .register();

    // endregion

    public static <T extends IComponentItem> NonNullConsumer<T> attach(IItemComponent components) {
        return item -> item.attachComponents(components);
    }

    public static <T extends IComponentItem> NonNullConsumer<T> attach(IItemComponent... components) {
        return item -> item.attachComponents(components);
    }

    public static void init(){

    }
}

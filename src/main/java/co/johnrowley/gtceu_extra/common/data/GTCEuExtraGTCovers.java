package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import co.johnrowley.gtceu_extra.common.cover.BatteryManagementCover;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.client.renderer.cover.ICoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;

import java.util.Arrays;
import java.util.Locale;

import static com.gregtechceu.gtceu.common.data.GTCovers.ALL_TIERS_WITH_ULV;

public class GTCEuExtraGTCovers {
    public static CoverDefinition[] BATTERY_MANAGEMENT =         BATTERY_MANAGEMENT = registerTiered(
            "batterymanagement", BatteryManagementCover::new,
            tier -> new SimpleCoverRenderer(GTCEuExtra.id("block/cover/battery_management")), GTValues.tiersBetween(GTValues.ULV, GTValues.HV));;

    public static CoverDefinition[] registerTiered(String id,
                                                   CoverDefinition.TieredCoverBehaviourProvider behaviorCreator,
                                                   Int2ObjectFunction<ICoverRenderer> coverRenderer, int... tiers) {
        return Arrays.stream(tiers).mapToObj(tier -> {
            var name = id + "." + GTValues.VN[tier].toLowerCase(Locale.ROOT);
            return register(name, (def, coverable, side) -> behaviorCreator.create(def, coverable, side, tier),
                    coverRenderer.apply(tier));
        }).toArray(CoverDefinition[]::new);
    }

    public static CoverDefinition[] registerTiered(String id,
                                                   CoverDefinition.TieredCoverBehaviourProvider behaviorCreator,
                                                   int... tiers) {
        return Arrays.stream(tiers).mapToObj(tier -> {
            var name = id + "." + GTValues.VN[tier].toLowerCase(Locale.ROOT);
            return register(name, (def, coverable, side) -> behaviorCreator.create(def, coverable, side, tier));
        }).toArray(CoverDefinition[]::new);
    }

    public static CoverDefinition register(String id, CoverDefinition.CoverBehaviourProvider behaviorCreator) {
        return register(id, behaviorCreator, new SimpleCoverRenderer(GTCEuExtra.id("block/cover/" + id)));
    }

    public static CoverDefinition register(String id, CoverDefinition.CoverBehaviourProvider behaviorCreator,
                                           ICoverRenderer coverRenderer) {
        var definition = new CoverDefinition(GTCEuExtra.id(id), behaviorCreator, coverRenderer);
        GTRegistries.COVERS.register(GTCEuExtra.id(id), definition);
        return definition;
    }

    public static void init(){
//        BATTERY_MANAGEMENT = registerTiered(
//            "batterymanagement", BatteryManagementCover::new,
//            tier -> new SimpleCoverRenderer(GTCEuExtra.id("block/cover/overlay_solar_panel")), ALL_TIERS_WITH_ULV);
    }
}

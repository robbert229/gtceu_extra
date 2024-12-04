package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import co.johnrowley.gtceu_extra.client.renderer.cover.BatteryManagementCoverRenderer;
import co.johnrowley.gtceu_extra.common.cover.BatteryManagementCover;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.client.renderer.cover.ICoverRenderer;
import com.gregtechceu.gtceu.client.renderer.cover.SimpleCoverRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;

import java.util.Arrays;
import java.util.Locale;

public class GTCEuExtraGTCovers {
    public static CoverDefinition[] BATTERY_MANAGEMENT =         BATTERY_MANAGEMENT = registerTiered(
            "battery_management", BatteryManagementCover::new,
            tier -> BatteryManagementCoverRenderer.INSTANCE, GTValues.tiersBetween(GTValues.ULV, GTValues.HV));;

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

    }
}

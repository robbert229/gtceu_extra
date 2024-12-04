package co.johnrowley.gtceu_extra.data.recipe;

import co.johnrowley.gtceu_extra.api.tag.GTCEuExtraTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class GTCEuExtraTags {
    public static final TagKey<Item> ALGAE = GTCEuExtraTagUtil.createModItemTag("algae");

    public static final TagKey<Item> BROWN_ALGAE = GTCEuExtraTagUtil.createModItemTag("brown_algae");
    public static final TagKey<Item> GOLDEN_BROWN_ALGAE = GTCEuExtraTagUtil.createModItemTag("golden_brown_algae");
    public static final TagKey<Item> GREEN_ALGAE = GTCEuExtraTagUtil.createModItemTag("green_algae");
    public static final TagKey<Item> RED_ALGAE = GTCEuExtraTagUtil.createModItemTag("red_algae");

    public static final TagKey<Fluid> EUTROPHIC_WATER = GTCEuExtraTagUtil.createModFluidTag("eutrophic_water");

    public static final TagKey<Fluid> BROWN_EUTROPHIC_WATER = GTCEuExtraTagUtil.createModFluidTag("brown_eutrophic_water");
    public static final TagKey<Fluid> GOLDEN_BROWN_EUTROPHIC_WATER = GTCEuExtraTagUtil.createModFluidTag("golden_brown_eutrophic_water");
    public static final TagKey<Fluid> RED_EUTROPHIC_WATER = GTCEuExtraTagUtil.createModFluidTag("red_eutrophic_water");
    public static final TagKey<Fluid> GREEN_EUTROPHIC_WATER = GTCEuExtraTagUtil.createModFluidTag("green_eutrophic_water");

    public static final TagKey<Item> CELLULOSE = GTCEuExtraTagUtil.createModItemTag("cellulose");

    public static final TagKey<Item> BROWN_CELLULOSE = GTCEuExtraTagUtil.createModItemTag("brown_cellulose");
    public static final TagKey<Item> GOLDEN_BROWN_CELLULOSE = GTCEuExtraTagUtil.createModItemTag("golden_brown_cellulose");
    public static final TagKey<Item> RED_CELLULOSE = GTCEuExtraTagUtil.createModItemTag("red_cellulose");
    public static final TagKey<Item> GREEN_CELLULOSE = GTCEuExtraTagUtil.createModItemTag("green_cellulose");

    public static void init(){
        //
    }
}

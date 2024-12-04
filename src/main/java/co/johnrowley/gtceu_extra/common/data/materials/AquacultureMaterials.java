package co.johnrowley.gtceu_extra.common.data.materials;

import co.johnrowley.gtceu_extra.GTCEuExtra;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static co.johnrowley.gtceu_extra.common.data.GTCEuExtraMaterials.*;

public class AquacultureMaterials {
    public static void register() {

        BrownEutrophicWater = new Material.Builder(GTCEuExtra.id("brown_eutrophic_water"))
                .fluid()
                .color(0x5e0d00)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        GreenEutrophicWater = new Material.Builder(GTCEuExtra.id("green_eutrophic_water"))
                .fluid()
                .color(0x47ac4a)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        RedEutrophicWater = new Material.Builder(GTCEuExtra.id("red_eutrophic_water"))
                .fluid()
                .color(0xe5281d)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        GoldenBrownEutrophicWater = new Material.Builder(GTCEuExtra.id("golden_brown_eutrophic_water"))
                .fluid()
                .color(0xf39900)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
    }
}

package co.johnrowley.gtceu_extra.common.data;

import co.johnrowley.gtceu_extra.common.data.materials.AquacultureMaterials;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;

public class GTCEuExtraMaterials {
    public static Material BrownEutrophicWater;
    public static Material GreenEutrophicWater;
    public static Material RedEutrophicWater;
    public static Material GoldenBrownEutrophicWater;

    public static void init(){
        AquacultureMaterials.register();
    }
}

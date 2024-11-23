package co.johnrowley.gtceu_extra.common.data;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static co.johnrowley.gtceu_extra.registry.GTCEuExtraRegistry.GTCEU_EXTRA_REGISTRATE;

@SuppressWarnings("unused")
public class GTCEuExtraBlocks {
    static {
        GTCEU_EXTRA_REGISTRATE.creativeModeTab(() -> GTCEuExtraCreativeModeTabs.GTCEU_EXTRA);
    }

    public static final BlockEntry<Block> TEST = GTCEU_EXTRA_REGISTRATE
            .block("test", Block::new)
            .lang("Test")
            .initialProperties(() -> Blocks.COBBLESTONE)
            .simpleItem()
            .register();

    public static void init(){
        //
    }
}

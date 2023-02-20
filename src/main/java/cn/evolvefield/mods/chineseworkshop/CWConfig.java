package cn.evolvefield.mods.chineseworkshop;

import snownee.kiwi.config.KiwiConfig;
import snownee.kiwi.config.KiwiConfig.Path;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@KiwiConfig
public class CWConfig {
	/* off */
    @Path("adjustmentStick.allowedClasses")
    public static List<String> allowedClasses = Arrays.asList(
            "net.minecraft.block.HugeMushroomBlock",
            "net.minecraft.block.FenceBlock",
            "net.minecraft.block.WallBlock",
            "net.minecraft.block.PaneBlock",
            "net.minecraft.block.StainedGlassPaneBlock",
            "net.minecraft.block.StairsBlock",
            "net.minecraft.block.GlazedTerracottaBlock"
    );
    /* on */

	@Path("adjustmentStick.allowedMods")
	public static List<String> allowedMods = Collections.singletonList(CW.MODID);

	@Path("adjustmentStick.showConversionJei")
	public static boolean showConversionJei = true;

	@Path("adjustmentStick.showRetextureJei")
	public static boolean showRetextureJei = true;

}

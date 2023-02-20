package cn.evolvefield.mods.chineseworkshop;


import cn.evolvefield.mods.chineseworkshop.common.block.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.Shapes;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.item.ModItem;

@KiwiModule("blocks")
@KiwiModule.Category
public class BlockModule extends AbstractModule {
	@KiwiModule.NoCategory
	public static final ModItem LOGO = new ModItem(itemProp());

	public static final CreativeModeTab GROUP = itemCategory(CW.MODID, "blocks", () -> new ItemStack(LOGO)).build();

	public static final ModBlock RED_STAINED_WOODEN_PLANKS = new ModBlock(blockProp(Material.WOOD));

	public static final ModBlock BLACK_BRICK_BLOCK = new ModBlock(blockProp(Material.STONE));

	public static final ModStairsBlock BLACK_BRICK_STAIRS = new ModStairsBlock(BLACK_BRICK_BLOCK.defaultBlockState());

	public static final SlabBlock BLACK_BRICK_SLAB = new SlabBlock(blockProp(BLACK_BRICK_BLOCK));

	public static final ModBlock WHITE_ASH_BLOCK = new ModBlock(blockProp(Material.STONE));

	public static final ModStairsBlock WHITE_ASH_STAIRS = new ModStairsBlock(WHITE_ASH_BLOCK.defaultBlockState());

	public static final SlabBlock WHITE_ASH_SLAB = new SlabBlock(blockProp(WHITE_ASH_BLOCK));

	public static final ModBlock RAMMED_EARTH = new ModBlock(blockProp(Material.DIRT));

	public static final ModStairsBlock RAMMED_EARTH_STAIRS = new ModStairsBlock(RAMMED_EARTH.defaultBlockState());

	public static final SlabBlock RAMMED_EARTH_SLAB = new SlabBlock(blockProp(RAMMED_EARTH));

	public static final ModBlock ANDESITE_PAVEMENT = new ModBlock(blockProp(Material.STONE));

	public static final ModStairsBlock ANDESITE_PAVEMENT_STAIRS = new ModStairsBlock(ANDESITE_PAVEMENT.defaultBlockState());

	public static final SlabBlock ANDESITE_PAVEMENT_SLAB = new SlabBlock(blockProp(ANDESITE_PAVEMENT));

	public static final RotatedPillarBlock RED_PILLAR = new RotatedPillarBlock(blockProp(Material.WOOD));

	public static final RotatedPillarBlock DARK_GREEN_PILLAR = new RotatedPillarBlock(blockProp(Material.WOOD));


	//黑瓦
	public static final RoofTileBlock BLACK_TILE_ROOF = new RoofTileBlock(blockProp(Material.STONE).noOcclusion(), false);

	public static final RoofTileJBlock BLACK_TILE_ROOF_J = new RoofTileJBlock(blockProp(BLACK_TILE_ROOF), false);

	public static final RoofTileJBlock BLACK_TILE_ROOF_RIDGE_J = new RoofTileJBlock(blockProp(BLACK_TILE_ROOF), Block.box(0, 0, 0, 16, 9, 16), false);

	public static final RoofTileRidgeBlock BLACK_TILE_ROOF_RIDGE = new RoofTileRidgeBlock(blockProp(BLACK_TILE_ROOF), Block.box(0, 0, 0, 16, 9, 16), false);

	public static final RoofTileRidgeBlock BLACK_TILE_ROOF_RIDGE_TOP = new RoofTileRidgeBlock(blockProp(BLACK_TILE_ROOF), Shapes.block(), false);

	public static final SlabRoofTileBlock BLACK_TILE_ROOF_SLAB = new SlabRoofTileBlock(blockProp(BLACK_TILE_ROOF), Block.box(0, 0, 0, 16, 8, 16), false);

	public static final SlabRoofTileBlock BLACK_TILE_ROOF_SLAB_TOP = new SlabRoofTileBlock(blockProp(BLACK_TILE_ROOF), Shapes.block(), false);

	//琉璃
	public static final RoofTileBlock YELLOW_TILE_ROOF = new RoofTileBlock(blockProp(Material.STONE).noOcclusion(), false);

	public static final RoofTileJBlock YELLOW_TILE_ROOF_J = new RoofTileJBlock(blockProp(YELLOW_TILE_ROOF), false);

	public static final RoofTileJBlock YELLOW_TILE_ROOF_RIDGE_J = new RoofTileJBlock(blockProp(YELLOW_TILE_ROOF), Block.box(0, 0, 0, 16, 9, 16), false);

	public static final RoofTileRidgeBlock YELLOW_TILE_ROOF_RIDGE = new RoofTileRidgeBlock(blockProp(YELLOW_TILE_ROOF), Block.box(0, 0, 0, 16, 9, 16), false);

	public static final RoofTileRidgeBlock YELLOW_TILE_ROOF_RIDGE_TOP = new RoofTileRidgeBlock(blockProp(YELLOW_TILE_ROOF), Shapes.block(), false);

	public static final SlabRoofTileBlock YELLOW_TILE_ROOF_SLAB = new SlabRoofTileBlock(blockProp(YELLOW_TILE_ROOF), Block.box(0, 0, 0, 16, 8, 16), false);

	public static final SlabRoofTileBlock YELLOW_TILE_ROOF_SLAB_TOP = new SlabRoofTileBlock(blockProp(YELLOW_TILE_ROOF), Shapes.block(), false);



	//茅草
	public static final RoofTileBlock THATCH_ROOF = new RoofTileBlock(blockProp(Material.WOOD).noOcclusion(), false);

	public static final RoofTileRidgeBlock THATCH_ROOF_RIDGE = new RoofTileRidgeBlock(blockProp(THATCH_ROOF), Block.box(0, 0, 0, 16, 9, 16), false);

	public static final RoofTileRidgeBlock THATCH_ROOF_RIDGE_TOP = new RoofTileRidgeBlock(blockProp(THATCH_ROOF), Shapes.block(), false);

	public static final SlabRoofTileBlock THATCH_ROOF_SLAB = new SlabRoofTileBlock(blockProp(THATCH_ROOF), Block.box(0, 0, 0, 16, 8, 16), false);

	public static final SlabRoofTileBlock THATCH_ROOF_SLAB_TOP = new SlabRoofTileBlock(blockProp(THATCH_ROOF), Shapes.block(), false);




	public static final ModBlock BLACK_CLAY_BLOCK = new ModBlock(blockProp(Blocks.CLAY));

	public static final ModBlock YELLOW_CLAY_BLOCK = new ModBlock(blockProp(Blocks.CLAY));

	public static final ModBlock PAINTED_BLOCK = new ModBlock(blockProp(Material.STONE));


}

package cn.evolvefield.mods.chineseworkshop;


import cn.evolvefield.mods.chineseworkshop.client.EmptyEntityRenderer;
import cn.evolvefield.mods.chineseworkshop.common.block.*;
import cn.evolvefield.mods.chineseworkshop.common.entity.Seat;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.Shapes;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.loader.event.ClientInitEvent;

@KiwiModule("decorations")
@KiwiModule.Category
public class DecorationModule extends AbstractModule {
	public static final ModHorizontalBlock DOUGONG = new ModHorizontalBlock(blockProp(Material.WOOD), Block.box(0, 11, 0, 16, 16, 13), false);

	public static final CreativeModeTab GROUP = itemCategory(CW.MODID, "decorations", () -> new ItemStack(DOUGONG)).build();

	//灯
	@KiwiModule.NoCategory
	@KiwiModule.NoItem
	public static final CandleBlock CANDLE = new CandleBlock(blockProp(Material.DECORATION).strength(0).lightLevel($ -> 14).sound(SoundType.WOOD));
	@KiwiModule.NoCategory
	public static final WallCandleBlock WALL_CANDLE = new WallCandleBlock(blockProp(Material.DECORATION).strength(0).lightLevel($ -> 14).sound(SoundType.WOOD));
	@KiwiModule.Name("candle")
	public static final StandingAndWallBlockItem CANDLE_ITEM = new StandingAndWallBlockItem(CANDLE, WALL_CANDLE, itemProp(), Direction.DOWN);
	@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT_MIPPED)
	public static final ModBlock RED_LANTERN = new LoggableBlock(blockProp(Material.DECORATION).strength(0).lightLevel($ -> 15).sound(SoundType.GRASS));
	@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT_MIPPED)
	public static final ModBlock WHITE_LANTERN = new LoggableBlock(blockProp(Material.DECORATION).strength(0).lightLevel($ -> 15).sound(SoundType.GRASS));
	public static final LoggableBlock STONE_TOWER_LAMP = new LoggableBlock(blockProp(Material.DECORATION).lightLevel($ -> 14).sound(SoundType.GLASS));
	public static final LoggableBlock PALACE_LANTERN = new LoggableBlock(blockProp(Material.DECORATION).lightLevel($ -> 14).sound(SoundType.GLASS));


	//门槛
	public static final ModHorizontalBlock WOODEN_THRESHOLD = new ModHorizontalBlock(blockProp(Material.WOOD), Shapes.or(Block.box(0, 0, 3, 16, 5, 12), Block.box(0, 5, 2, 16, 7.5, 13)), false);
	public static final ModHorizontalBlock STONE_THRESHOLD = new ModHorizontalBlock(blockProp(Material.WOOD), Shapes.or(Block.box(0, 0, 3, 16, 5, 12), Block.box(0, 5, 2, 16, 7.5, 13)), false);

	//门墩
	public static final ModHorizontalBlock MENDUN = new ModHorizontalBlock(blockProp(Material.STONE), Block.box(1, 0, 0, 15, 16, 16), false);

	public static final BenchBlock BENCH = new BenchBlock(blockProp(Material.WOOD), false);
	public static final ChairBlock CHAIR = new ChairBlock(blockProp(Material.WOOD), false);

	public static final TableBlock TABLE = new TableBlock(blockProp(Material.WOOD));

	public static final Direction2Block CARVING = new Direction2Block(blockProp(Material.WOOD), Block.box(0, 4, 5, 16, 16, 11), false);

	public static final EntityType<?> SEAT = EntityType.Builder.createNothing(MobCategory.MISC).setCustomClientFactory((spawnEntity, world) -> new Seat(world)).sized(0.0001F, 0.0001F).setTrackingRange(16).setUpdateInterval(20).build(CW.MODID + ".seat");


	//墙
	public static final SmallFenceBlock WHITE_ASH_WALL = new SmallFenceBlock(blockProp(Material.STONE));
	public static final SmallFenceBlock RAMMED_EARTH_WALL = new SmallFenceBlock(blockProp(Material.DIRT));
	public static final SmallFenceBlock BLACK_BRICK_WALL = new SmallFenceBlock(blockProp(Material.STONE));
	public static final SmallFenceBlock OAK_WALL = new SmallFenceBlock(blockProp(Material.WOOD));
	public static final SmallFenceBlock PAINTED_WALL = new SmallFenceBlock(blockProp(Material.STONE));
	public static final SmallFenceBlock RED_STAINED_WOODEN_PLANKS_WALL = new SmallFenceBlock(blockProp(Material.WOOD));



	//栅栏
	public static final SmallFenceBlock OAK_CHINESE_FENCE = new SmallFenceBlock(blockProp(Material.WOOD), 1.5F, 1F, 19, 14, 24);
	public static final SmallFenceBlock DIORITE_FENCE = new SmallFenceBlock(blockProp(Material.STONE), 2F, 1F, 19, 14, 24);
	public static final AndesiteFenceBlock ANDESITE_FENCE = new AndesiteFenceBlock(blockProp(Material.STONE));




	//挂落
	public static final SmallFenceBlock LITHEL_DECO = new SmallFenceBlock(blockProp(Material.WOOD), 1.0F, 1.0F, 16.0F, 16.0F, 16.0F);

	//窗子
	public static final IronBarsBlock PAPER_WINDOW = new StainedGlassPaneBlock(DyeColor.WHITE, blockProp(Material.WOOL));
	public static final SmallFenceBlock WOODEN_WINDOW = new SmallFenceBlock(blockProp(Material.WOOD), 1.5F, 1.5F, 16.0F, 16.0F, 16.0F);
	public static final SmallFenceBlock DIORITE_WINDOW = new SmallFenceBlock(blockProp(Material.STONE), 1.5F, 1.5F, 16.0F, 16.0F, 16.0F);
	public static final IronBarsBlock RED_STAINED_WOODEN_PLANKS_PAPER_WINDOW = new StainedGlassPaneBlock(DyeColor.RED, blockProp(Material.WOOL));


	public static final HighDoorBlock HIGH_DOOR = new HighDoorBlock(blockProp(Material.WOOD));

	public static final TagKey<Block> THRESHOLD = blockTag(CW.MODID, "threshold");


	@Override
	protected void clientInit(ClientInitEvent event) {
		EntityRenderers.register(SEAT, EmptyEntityRenderer::new);
	}

}

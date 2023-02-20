package cn.evolvefield.mods.chineseworkshop;


import cn.evolvefield.mods.chineseworkshop.common.block.*;
import cn.evolvefield.mods.chineseworkshop.common.tile.CWTextureTile;
import cn.evolvefield.mods.chineseworkshop.init.event.RetextureIngredientEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.item.ModBlockItem;
import snownee.kiwi.loader.event.ClientInitEvent;
import snownee.kiwi.loader.event.InitEvent;
import snownee.kiwi.util.NBTHelper;
import snownee.kiwi.util.Util;

import java.util.List;

@KiwiModule("retexture")
@KiwiModule.Optional
@KiwiModule.Category()
@KiwiModule.Subscriber(modBus = true)
public class TextureModule extends AbstractModule {
	public static final RoofTileBlock BLACK_TILE_ROOF_DYN = new RoofTileBlock(blockProp(BlockModule.BLACK_TILE_ROOF), true);

	public static final RoofTileJBlock BLACK_TILE_ROOF_J_DYN = new RoofTileJBlock(blockProp(BlockModule.BLACK_TILE_ROOF_J), true);

	public static final RoofTileJBlock BLACK_TILE_ROOF_RIDGE_J_DYN = new RoofTileJBlock(blockProp(BlockModule.BLACK_TILE_ROOF_RIDGE_J), Block.box(0, 0, 0, 16, 9, 16), true);

	public static final RoofTileRidgeBlock BLACK_TILE_ROOF_RIDGE_DYN = new RoofTileRidgeBlock(blockProp(BlockModule.BLACK_TILE_ROOF_RIDGE), Block.box(0, 0, 0, 16, 9, 16), true);

	public static final RoofTileRidgeBlock BLACK_TILE_ROOF_RIDGE_TOP_DYN = new RoofTileRidgeBlock(blockProp(BlockModule.BLACK_TILE_ROOF_RIDGE_TOP), Shapes.block(), true);

	public static final SlabRoofTileBlock BLACK_TILE_ROOF_SLAB_DYN = new SlabRoofTileBlock(blockProp(BlockModule.BLACK_TILE_ROOF_SLAB), Block.box(0, 0, 0, 16, 8, 16), true);

	public static final SlabRoofTileBlock BLACK_TILE_ROOF_SLAB_TOP_DYN = new SlabRoofTileBlock(blockProp(BlockModule.BLACK_TILE_ROOF_SLAB_TOP), Shapes.block(), true);

	public static final BenchBlock BENCH_DYN = new BenchBlock(blockProp(DecorationModule.BENCH), true);

	public static final ChairBlock CHAIR_DYN = new ChairBlock(blockProp(DecorationModule.CHAIR), true);

	/* off */
    public static final BlockEntityType<?> RETEXTURE = BlockEntityType.Builder.of(CWTextureTile::new,
            BLACK_TILE_ROOF_DYN,
            BLACK_TILE_ROOF_J_DYN,
            BLACK_TILE_ROOF_RIDGE_J_DYN,
            BLACK_TILE_ROOF_RIDGE_DYN,
            BLACK_TILE_ROOF_RIDGE_TOP_DYN,
            BLACK_TILE_ROOF_SLAB_DYN,
            BLACK_TILE_ROOF_SLAB_TOP_DYN,
            BENCH_DYN,
            CHAIR_DYN
    ).build(null);
    /* on */

	@Override
	protected void init(InitEvent event) {
		super.init(event);
		MinecraftForge.EVENT_BUS.addListener(this::onRetextureIngredient);
	}

	public void onRetextureIngredient(RetextureIngredientEvent event) {
		event.add(BlockModule.BLACK_TILE_ROOF);
		event.add(BlockModule.BLACK_TILE_ROOF_J);
		event.add(BlockModule.BLACK_TILE_ROOF_RIDGE_J);
		event.add(BlockModule.BLACK_TILE_ROOF_RIDGE);
		event.add(BlockModule.BLACK_TILE_ROOF_RIDGE_TOP);
		event.add(BlockModule.BLACK_TILE_ROOF_SLAB);
		event.add(BlockModule.BLACK_TILE_ROOF_SLAB_TOP);
		event.add(DecorationModule.BENCH);
		event.add(DecorationModule.CHAIR);
	}

	@OnlyIn(Dist.CLIENT)
	//@SubscribeEvent
	public void onModelBake(ModelEvent.ModifyBakingResult event) {
		ModBlockItem.INSTANT_UPDATE_TILES.add(RETEXTURE);

		Block block;
		BlockState state;

		block = BLACK_TILE_ROOF_DYN;
		state = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_J_DYN;
		state = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);

		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_RIDGE_J_DYN;
		state = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_RIDGE_DYN;
		state = block.defaultBlockState().setValue(RoofTileRidgeBlock.VARIANT, RoofTileRidgeBlock.Variant.I_90);
		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_RIDGE_TOP_DYN;
		state = block.defaultBlockState().setValue(RoofTileRidgeBlock.VARIANT, RoofTileRidgeBlock.Variant.I_90);
		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_SLAB_DYN;
		state = block.defaultBlockState();
		registryModel(event, block, state);

		block = BLACK_TILE_ROOF_SLAB_TOP_DYN;
		state = block.defaultBlockState();
		registryModel(event, block, state);

		block = BENCH_DYN;
		state = block.defaultBlockState().setValue(Direction2Block.FACING, Direction2.SOUTH_NORTH);
		registryModel(event, block, state);

		block = CHAIR_DYN;
		state = block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
		registryModel(event, block, state);
	}


	public static void registryModel( ModelEvent.ModifyBakingResult event, Block block, BlockState state){

		block.getStateDefinition().getPossibleStates().forEach((s) -> {

			ResourceLocation rl = ModelLocationUtils.getModelLocation(s.getBlock());
			var rl2 = new ModelResourceLocation(new ResourceLocation(rl.getNamespace(), rl.getPath()), "inventory");
			BakedModel bakedModel = (BakedModel)event.getModelBakery().getModel(rl2);
			if (s.equals(state) && bakedModel != null && block != null && block.asItem() != null) {
				event.getModels().put(rl2, bakedModel);
				var mesher = Minecraft.getInstance().getItemRenderer().getItemModelShaper();
				mesher.register(block.asItem(), rl2);
			}

		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void addTooltip(ItemStack stack, List<Component> tooltip, String langKey) {
		NBTHelper data = NBTHelper.of(stack);
		ResourceLocation rl = Util.RL(data.getString("BlockEntityTag.Items.main"));
		if (rl != null) {
			Item item = BuiltInRegistries.ITEM.get(rl);
			if (item != null) {
				String name = I18n.get(item.getDescriptionId());
				tooltip.add(Component.translatable("chineseworkshop.tip." + langKey, name).withStyle(ChatFormatting.GRAY));
			}
		}
	}


	@Override
	protected void clientInit(ClientInitEvent event) {
		ModBlockItem.INSTANT_UPDATE_TILES.add(RETEXTURE);// 使用户在放下方块时贴图能立即更新
	}
}

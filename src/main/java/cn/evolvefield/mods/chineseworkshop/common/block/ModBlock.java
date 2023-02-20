package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import snownee.kiwi.block.entity.BaseBlockEntity;
import snownee.kiwi.mixin.BlockAccess;
import snownee.kiwi.util.VanillaActions;

/**
 * Project: chineseworkshop
 * Author: cnlimiter
 * Date: 2023/2/20 22:47
 * Description:
 */
public class ModBlock extends Block{
    public ModBlock(BlockBehaviour.Properties builder) {
        super(builder);
    }

    public static SoundType deduceSoundType(Material material) {
        if (material != Material.WOOD && material != Material.VEGETABLE) {
            if (material != Material.DIRT && material != Material.CLAY) {
                if (material != Material.PLANT && material != Material.GRASS && material != Material.REPLACEABLE_PLANT && material != Material.LEAVES && material != Material.SPONGE && material != Material.EXPLOSIVE) {
                    if (material != Material.REPLACEABLE_WATER_PLANT && material != Material.WATER_PLANT) {
                        if (material == Material.METAL) {
                            return SoundType.METAL;
                        } else if (material != Material.GLASS && material != Material.PORTAL && material != Material.ICE && material != Material.ICE_SOLID && material != Material.BUILDABLE_GLASS) {
                            if (material != Material.WOOL && material != Material.CLOTH_DECORATION && material != Material.CACTUS && material != Material.CAKE && material != Material.FIRE) {
                                if (material == Material.SAND) {
                                    return SoundType.SAND;
                                } else if (material != Material.SNOW && material != Material.TOP_SNOW) {
                                    return material == Material.HEAVY_METAL ? SoundType.ANVIL : SoundType.STONE;
                                } else {
                                    return SoundType.SNOW;
                                }
                            } else {
                                return SoundType.WOOL;
                            }
                        } else {
                            return SoundType.GLASS;
                        }
                    } else {
                        return SoundType.WET_GRASS;
                    }
                } else {
                    return SoundType.GRASS;
                }
            } else {
                return SoundType.GRAVEL;
            }
        } else {
            return SoundType.WOOD;
        }
    }

    public static float deduceHardness(Material material) {
        if (material != Material.PLANT && material != Material.AIR && material != Material.FIRE) {
            if (material == Material.STONE) {
                return 2.5F;
            } else if (material == Material.WOOD) {
                return 2.0F;
            } else if (material == Material.GRASS) {
                return 0.6F;
            } else if (material != Material.SAND && material != Material.DIRT && material != Material.CLAY) {
                if (material == Material.GLASS) {
                    return 0.3F;
                } else if (material == Material.CACTUS) {
                    return 0.4F;
                } else if (material != Material.METAL && material != Material.HEAVY_METAL) {
                    if (material == Material.WEB) {
                        return 4.0F;
                    } else if (material == Material.WOOL) {
                        return 0.8F;
                    } else {
                        return material != Material.WATER && material != Material.LAVA ? 1.0F : 100.0F;
                    }
                } else {
                    return 5.0F;
                }
            } else {
                return 0.5F;
            }
        } else {
            return 0.0F;
        }
    }

    public static ItemStack pick(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().getCloneItemStack(world, pos, state);
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof BaseBlockEntity && !tile.onlyOpCanSetNbt() && ((BaseBlockEntity)tile).persistData) {
            CompoundTag data = tile.saveWithFullMetadata();
            data.remove("x");
            data.remove("y");
            data.remove("z");
            BlockItem.setBlockEntityData(stack, tile.getType(), data);
        }

        return stack;
    }

    public static void setFireInfo(Block block) {
        Material material = ((BlockAccess)block).getMaterial();
        int fireSpreadSpeed = 0;
        int flammability = 0;
        if (material == Material.WOOD) {
            if (!(block instanceof DoorBlock) && !(block instanceof TrapDoorBlock) && !(block instanceof ButtonBlock) && !(block instanceof PressurePlateBlock)) {
                fireSpreadSpeed = 5;
                flammability = 20;
            }
        } else if (material != Material.PLANT && material != Material.REPLACEABLE_PLANT) {
            if (material == Material.CLOTH_DECORATION) {
                fireSpreadSpeed = 60;
                flammability = 20;
            } else if (material == Material.LEAVES) {
                fireSpreadSpeed = 30;
                flammability = 60;
            } else if (material == Material.WOOL) {
                fireSpreadSpeed = 30;
                flammability = 60;
            }
        } else if (!(block instanceof SaplingBlock)) {
            fireSpreadSpeed = 30;
            flammability = 100;
        }

        if (fireSpreadSpeed != 0) {
            VanillaActions.setFireInfo(block, fireSpreadSpeed, flammability);
        }

    }
}

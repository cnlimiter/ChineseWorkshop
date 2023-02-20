
package cn.evolvefield.mods.chineseworkshop.init.handler;

import cn.evolvefield.mods.chineseworkshop.common.entity.Seat;
import cn.evolvefield.mods.chineseworkshop.core.ISeat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import snownee.kiwi.schedule.Scheduler;
import snownee.kiwi.schedule.impl.SimpleGlobalTask;

import java.util.List;

@Mod.EventBusSubscriber
public class SittingHandler {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getLevel().isClientSide) {
			return;
		}

		var player = event.getEntity();
		if (player instanceof FakePlayer || player.getVehicle() != null)
			return;

		var stack1 = player.getMainHandItem();
		var stack2 = player.getOffhandItem();
		if (!stack1.isEmpty() || !stack2.isEmpty())
			return;

		var world = event.getLevel();
		BlockPos pos = event.getPos();

		var vec = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

		double maxDist = 2;
		if ((vec.x - player.getX()) * (vec.x - player.getX()) + (vec.y - player.getY()) * (vec.y - player.getY()) + (vec.z - player.getZ()) * (vec.z - player.getZ()) > maxDist * maxDist)
			return;

		BlockState state = world.getBlockState(pos);

		if (state.getBlock() instanceof ISeat) {
			ISeat iseat = (ISeat) state.getBlock();
			if (!iseat.hasSeat(world, pos)) {
				return;
			}
			List<Seat> seats = world.getEntitiesOfClass(Seat.class, new AABB(pos, pos.offset(1, 1, 1)));

			if (seats.isEmpty()) {
				var v = iseat.getSeat(state, world, pos);
				Seat seat = new Seat(world, v.add(pos.getX(), pos.getY(), pos.getZ()));
				world.addFreshEntity(seat);
				Scheduler.add(new SimpleGlobalTask(LogicalSide.SERVER, Phase.END, i -> {
					if (player.isPassenger()) {
						return true;
					}
					if (i > 3) {
						player.startRiding(seat);
						return true;
					}
					return false;
				}));
				event.setCanceled(true);
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}
}

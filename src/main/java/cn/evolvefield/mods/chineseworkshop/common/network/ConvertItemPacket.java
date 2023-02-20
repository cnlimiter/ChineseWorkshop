package cn.evolvefield.mods.chineseworkshop.common.network;

import cn.evolvefield.mods.chineseworkshop.core.Selection;
import cn.evolvefield.mods.chineseworkshop.core.Selections;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.network.KiwiPacket;
import snownee.kiwi.network.PacketHandler;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Project: ChineseWorkShop
 * Author: cnlimiter
 * Date: 2023/2/19 11:13
 * Description:
 */
@KiwiPacket(value = "convert_item", dir = KiwiPacket.Direction.PLAY_TO_SERVER)
public class ConvertItemPacket extends PacketHandler {
        public static ConvertItemPacket packet;

        @Override
        public CompletableFuture<FriendlyByteBuf> receive(Function<Runnable, CompletableFuture<FriendlyByteBuf>> function, FriendlyByteBuf friendlyByteBuf, @Nullable ServerPlayer serverPlayer) {
            int index = friendlyByteBuf.readInt();
            return function.apply(() ->{
                if (serverPlayer != null){
                    var held = serverPlayer.getMainHandItem();
                    if (held.isEmpty() || index < 0) {
                        return;
                    }
                    var item = held.getItem();
                    Selection selection = Selections.find(item);
                    if (selection == null) {
                        return;
                    }
                    if (selection.matches(item)) {
                        ItemStack stack = new ItemStack(selection.get(index % selection.size()));
                        stack.setCount(held.getCount());
                        stack.setTag(held.getTag());
                        serverPlayer.setItemSlot(EquipmentSlot.MAINHAND, stack);
                    }
                }
            });
        }

        public static void send(int n) {
            packet.sendToServer($ -> $.writeVarInt(n));
        }

}

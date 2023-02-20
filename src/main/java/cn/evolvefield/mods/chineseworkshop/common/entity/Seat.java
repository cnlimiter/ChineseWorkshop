package cn.evolvefield.mods.chineseworkshop.common.entity;

import cn.evolvefield.mods.chineseworkshop.DecorationModule;
import cn.evolvefield.mods.chineseworkshop.core.ISeat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Project: chineseworkshop
 * Author: cnlimiter
 * Date: 2023/2/20 1:48
 * Description:
 */
public class Seat extends Entity{
    public Seat(Level Level, Vec3 pos) {
        this(Level);
        setPos(pos.x, pos.y + 0.001, pos.z);
    }

    public Seat(Level Level) {
        super(DecorationModule.SEAT, Level);
    }



    @Override
    public void tick() {
        if (this.blockPosition().getX() < -64.0D) {
            this.kill();
        }

        BlockPos pos = blockPosition();
        if (pos == null || !(getLevel().getBlockState(pos).getBlock() instanceof ISeat)) {
            remove(Entity.RemovalReason.KILLED);
            return;
        }

        List<Entity> passangers = getPassengers();
        for (Entity e : passangers)
            if (!e.isAlive() || e.isCrouching())
                e.stopRiding();
        if (passangers.isEmpty()) {
            if (++portalTime > 5) {
                remove(Entity.RemovalReason.KILLED);
            }
        } else {
            portalTime = 0;
        }

        this.firstTick = false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}

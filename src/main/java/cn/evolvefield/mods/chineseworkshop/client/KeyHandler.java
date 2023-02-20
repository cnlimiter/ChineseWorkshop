package cn.evolvefield.mods.chineseworkshop.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Project: chineseworkshop
 * Author: cnlimiter
 * Date: 2023/2/20 17:30
 * Description:
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyHandler {
    @SubscribeEvent
    public static void registryKeys(RegisterKeyMappingsEvent event){
        IKeyConflictContext noKeyConflict = new IKeyConflictContext()
        {
            @Override
            public boolean isActive()
            {
                return Minecraft.getInstance().screen==null;
            }

            @Override
            public boolean conflicts(IKeyConflictContext other)
            {
                return false;
            }
        };
        ClientHandler.kbSelect.setKeyConflictContext(noKeyConflict);
        event.register(ClientHandler.kbSelect);
    }
}

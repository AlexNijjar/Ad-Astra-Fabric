package earth.terrarium.ad_astra.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import org.apache.commons.lang3.NotImplementedException;

public class PlatformUtils {

    @ExpectPlatform
    public static <T extends Entity> T teleportToDimension(T entity, ServerWorld world, TeleportTarget target) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void registerStrippedLog(Block log, Block strippedLog) {
        throw new NotImplementedException();
    }
}
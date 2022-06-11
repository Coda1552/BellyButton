package teamdraco.bellybutton.common.dimension;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.ITeleporter;

public class NavelTeleporter implements ITeleporter {
    public final ServerLevel world;

    public NavelTeleporter(ServerLevel world) {
        this.world = world;
    }
}

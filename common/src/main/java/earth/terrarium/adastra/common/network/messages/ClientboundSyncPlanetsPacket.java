package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ClientboundSyncPlanetsPacket(
    Map<ResourceKey<Level>, Planet> planets
) implements Packet<ClientboundSyncPlanetsPacket> {

    public static final ClientboundPacketType<ClientboundSyncPlanetsPacket> TYPE = new Type();

    @Override
    public PacketType<ClientboundSyncPlanetsPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ClientboundSyncPlanetsPacket> implements ClientboundPacketType<ClientboundSyncPlanetsPacket> {

        public Type() {
            super(
                ClientboundSyncPlanetsPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "add_role"),
                ObjectByteCodec.create(
                    ByteCodec.passthrough(
                            (buf, a) -> AdAstraData.encodePlanets(new FriendlyByteBuf(buf)),
                            (buf) -> AdAstraData.decodePlanets(new FriendlyByteBuf(buf)).stream()
                                .collect(Collectors.toMap(Planet::dimension, Function.identity())))
                        .fieldOf(ClientboundSyncPlanetsPacket::planets),
                    ClientboundSyncPlanetsPacket::new));
        }

        @Override
        public Runnable handle(ClientboundSyncPlanetsPacket packet) {
            return () -> AdAstraData.setPlanets(packet.planets);
        }
    }
}
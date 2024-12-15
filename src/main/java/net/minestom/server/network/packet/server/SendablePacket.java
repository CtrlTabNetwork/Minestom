package net.minestom.server.network.packet.server;

import net.minestom.server.network.ConnectionState;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a packet that can be sent to a {@link PlayerConnection}.
 */
public sealed interface SendablePacket
        permits BufferedPacket, CachedPacket, FramedPacket, LazyPacket, ServerPacket {

    static @Nullable ServerPacket extractServerPacket(@NotNull ConnectionState state, @NotNull SendablePacket packet) {
        return switch (packet) {
            case ServerPacket serverPacket -> serverPacket;
            case CachedPacket cachedPacket -> cachedPacket.packet(state);
            case FramedPacket framedPacket -> framedPacket.packet();
            case LazyPacket lazyPacket -> lazyPacket.packet();
            case BufferedPacket bufferedPacket -> null;
        };
    }

    @ApiStatus.Experimental
    static @Nullable SendablePacket rewrapServerPacket(@NotNull SendablePacket oldPacket, @NotNull ServerPacket newPacket) {
        return switch (oldPacket) {
            case ServerPacket serverPacket -> newPacket;
            case CachedPacket cachedPacket -> new CachedPacket(newPacket);
            case FramedPacket framedPacket -> new FramedPacket(newPacket, framedPacket.body());
            case LazyPacket lazyPacket -> new LazyPacket(() -> newPacket);
            case BufferedPacket bufferedPacket -> null;
        };
    }
}

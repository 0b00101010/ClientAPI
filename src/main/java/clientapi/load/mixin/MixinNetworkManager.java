/*
 * Copyright 2018 ImpactDevelopment
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clientapi.load.mixin;

import clientapi.ClientAPI;
import clientapi.event.defaults.game.network.PacketEvent;
import clientapi.event.defaults.game.network.ServerEvent;
import clientapi.load.mixin.extension.INetworkManager;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.zero.alpine.event.EventState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

/**
 * @author Brady
 * @since 4/27/2017
 */
@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

    private boolean sendPackets = true;

    @Shadow protected abstract void dispatchPacket(final Packet<?> inPacket, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    @Shadow @Final private EnumPacketDirection direction;

    @SuppressWarnings("unchecked")
    @Redirect(
            method = "channelRead0",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V"
            )
    )
    private void channelRead0$processPacket(Packet<?> packetIn, INetHandler handler) {
        // Only call PacketEvent.Receive if this NetworkManager expects
        // CLIENTBOUND type incoming packets, indicating that this is a pure
        // client-sided NetworkManager and not from the Integrated Server.
        if (this.direction == EnumPacketDirection.CLIENTBOUND) {
            PacketEvent event = new PacketEvent.Receive(packetIn);
            ClientAPI.EVENT_BUS.post(event);
            if (event.isCancelled())
                return;

            packetIn = event.getPacket();
        }

        ((Packet<INetHandler>) packetIn).processPacket(handler);
    }

    @SuppressWarnings("AmbiguousMixinReference")
    @Redirect(
            method = "sendPacket",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/NetworkManager.dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"
            )
    )
    private void sendPacket$dispatchPacket(NetworkManager networkManager, Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<?super Void>>[] futureListeners) {
        // Only call PacketEvent.Send if this NetworkManager expects
        // CLIENTBOUND type incoming packets, indicating that this is a pure
        // client-sided NetworkManager and not from the Integrated Server.
        if (this.direction == EnumPacketDirection.CLIENTBOUND) {
            PacketEvent event = new PacketEvent.Send(packetIn);
            ClientAPI.EVENT_BUS.post(event);
            if (event.isCancelled())
                return;

            packetIn = event.getPacket();
        }

        this.dispatchPacket(packetIn, futureListeners);
    }

    @SuppressWarnings("AmbiguousMixinReference")
    @Redirect(
            method = "sendPacket",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/NetworkManager.isChannelOpen()Z"
            )
    )
    private boolean sendPacket$isChannelOpen(NetworkManager networkManager) {
        return this.sendPackets && networkManager.isChannelOpen();
    }

    @Inject(
            method = "handleDisconnection",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "net/minecraft/network/INetHandler.onDisconnect(Lnet/minecraft/util/text/ITextComponent;)V"
            )
    )
    private void onDisconnect(CallbackInfo ci) {
        ClientAPI.EVENT_BUS.post(new ServerEvent.Disconnect(EventState.POST, true, Minecraft.getMinecraft().getCurrentServerData()));
    }

    @Inject(
            method = "flushOutboundQueue",
            at = @At("HEAD"),
            cancellable = true
    )
    private void flushOutboundQueue(CallbackInfo ci) {
        if (!sendPackets)
            ci.cancel();
    }

    @Override
    public final void setSendPackets(boolean sendPackets) {
        this.sendPackets = sendPackets;
    }

    @Override
    public final boolean isSendPackets() {
        return this.sendPackets;
    }
}

package me.zero.client.load.inject.transformer.reference.obfuscation;

import io.netty.channel.ChannelHandlerContext;
import me.zero.client.load.inject.transformer.reference.ClassReference;
import me.zero.client.load.inject.transformer.reference.MethodReference;

import static me.zero.client.load.inject.transformer.reference.obfuscation.Obfuscation.MCP;
import static me.zero.client.load.inject.transformer.reference.obfuscation.Obfuscation.VANILLA;
import static me.zero.client.load.inject.transformer.reference.obfuscation.ObfuscationName.from;

/**
 * Contains class and method references for the game
 * that are required by the API's default transformers.
 *
 * @see me.zero.client.load.inject.transformer.ITransformer
 *
 * @since 1.0
 *
 * Created by Brady on 1/22/2017.
 */
public interface MCMappings {

    // Classes
    ClassReference Main = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.client.main.Main"), from(VANILLA, "net.minecraft.client.main.Main") });
    ClassReference Minecraft = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.client.Minecraft"), from(VANILLA, "bes") });
    ClassReference GuiIngame = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.client.gui.GuiIngame"), from(VANILLA, "bfh") });
    ClassReference NetHandlerPlayClient = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.client.network.NetHandlerPlayClient"), from(VANILLA, "bno") });
    ClassReference NetworkManager = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.network.NetworkManager"), from(VANILLA, "er") });
    ClassReference Packet = new ClassReference(new ObfuscationName[] { from(MCP, "net.minecraft.network.Packet"), from(VANILLA, "fm") });

    // Methods
    MethodReference main = new MethodReference(new ObfuscationName[] { from(MCP, "main"), from(VANILLA, "main")}, Void.TYPE);
    MethodReference runTick = new MethodReference(new ObfuscationName[] { from(MCP, "runTick"), from(VANILLA, "t") }, Void.TYPE);
    MethodReference runGameLoop = new MethodReference(new ObfuscationName[] { from(MCP, "runGameLoop"), from(VANILLA, "av") }, Void.TYPE);
    MethodReference startGame = new MethodReference(new ObfuscationName[] { from(MCP, "startGame"), from(VANILLA, "an") }, Void.TYPE);
    MethodReference renderGameOverlay = new MethodReference(new ObfuscationName[] { from(MCP, "renderGameOverlay"), from(VANILLA, "a") }, Void.TYPE, Float.TYPE);
    MethodReference channelRead0 = new MethodReference(new ObfuscationName[] { from(MCP, "channelRead0"), from(VANILLA, "a" )}, Void.TYPE, ChannelHandlerContext.class, Packet);
    MethodReference sendPacket1 = new MethodReference(new ObfuscationName[] { from(MCP, "sendPacket" ), from(VANILLA, "a")}, Void.TYPE, Packet);
}
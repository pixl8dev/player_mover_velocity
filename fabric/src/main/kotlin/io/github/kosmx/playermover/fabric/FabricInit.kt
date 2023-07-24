package io.github.kosmx.playermover.fabric


import com.mojang.brigadier.arguments.StringArgumentType
import io.github.kosmx.playermover.common.MoveMessage
import io.netty.buffer.Unpooled
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.command.CommandManager.argument
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

object FabricInit : ModInitializer {

    private val channelId = Identifier(MoveMessage.channelID)


    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->

            dispatcher.register(
                literal("moveplayer")
                    .then(
                        argument("player", EntityArgumentType.player())
                            .then(
                                argument("server", StringArgumentType.string())
                                    .executes { ctx ->
                                        val player = EntityArgumentType.getPlayer(ctx, "player")
                                        val server = StringArgumentType.getString(ctx, "server")
                                        executeCommand(player, server)
                                        15
                                    }
                            )
                    )
            )
        }
    }

    private fun executeCommand(player: ServerPlayerEntity, target: String) {
        val msg = MoveMessage().apply {
            this.player = player.uuid
            this.server = target
        }.write()

        ServerPlayNetworking.send(player, channelId, PacketByteBuf(Unpooled.wrappedBuffer(msg)))
    }

}
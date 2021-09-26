package io.github.kosmx.playermover.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import io.github.kosmx.playermover.common.MoveMessage;


public class PluginListener {

    final private Plugin plugin;

    public PluginListener(Plugin plugin){
        this.plugin = plugin;
    }

    @Subscribe
    public void onCommand(PluginMessageEvent event){
        if(event.getSource() instanceof ServerConnection connection && event.getIdentifier().getId().equals("playermover:move")){
            MoveMessage message = new MoveMessage();
            try {
                message.read(event.getData());
                var server = plugin.getServer().getServer(message.getServer());
                if(server.isPresent()){
                    var player = plugin.getServer().getPlayer(message.getPlayer());
                    if(player.isPresent()){
                        player.get().createConnectionRequest(server.get()).fireAndForget();
                        event.setResult(PluginMessageEvent.ForwardResult.handled());
                        return;
                    }
                    else plugin.getLogger().error("Player is null");
                }
                else plugin.getLogger().error("Server is null");
            }
            catch (Exception e){
                plugin.getLogger().error(e.getMessage());
                e.printStackTrace();
            }
        }
        plugin.getLogger().error("Can not send player to server");
    }
}

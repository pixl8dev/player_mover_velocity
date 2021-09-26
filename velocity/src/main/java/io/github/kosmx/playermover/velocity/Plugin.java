package io.github.kosmx.playermover.velocity;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import lombok.Getter;
import org.slf4j.Logger;

@com.velocitypowered.api.plugin.Plugin(id = "playermover", name = "Player mover", version = "3.1415", authors = "KosmX")
public class Plugin {
    @Getter
    private final ProxyServer server;
    @Getter
    private final Logger logger;

    @Inject
    public Plugin(ProxyServer server, Logger logger){
        //Load the plugin???
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitializaion(ProxyInitializeEvent event){
        server.getEventManager().register(this, new PluginListener(this));

        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("playermover", "move"));

    }
}

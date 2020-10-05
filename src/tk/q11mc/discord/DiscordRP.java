package tk.q11mc.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import tk.q11mc.Main;

public class DiscordRP {
    private volatile boolean running = true;
    private static long created = 0;

    public void start() {
        created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.discordInitialize("761987952036413450",handlers,true);
        System.out.println("Discord initialized");
        new Thread("Discord RP Callback") {
            @Override
            public void run() {
                while(running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String ip) {
        DiscordRichPresence richPresence = new DiscordRichPresence();
        richPresence.largeImageKey = "icon";
        richPresence.largeImageText = "We don't have a map yet";
        richPresence.partyMax = 0;
        switch (Main.gameState) {
            case LOADING -> {
                richPresence.state = "Waiting";
                richPresence.details = "Connecting with "+ip;
            }
            case MULTIPLAYER, PAUSE -> {
                richPresence.state = "Playing a game";
                richPresence.details = "Playing on "+ip;
            }
            case SINGLEPLAYER -> {
                richPresence.state = "Playing alone";
                richPresence.details = "Playing alone like a pussy";
            }
            default -> {
                richPresence.state = "Not Playing";
                richPresence.details = "In the main menu";
            }
        }
        richPresence.startTimestamp = created;
        DiscordRPC.discordUpdatePresence(richPresence);
    }
}

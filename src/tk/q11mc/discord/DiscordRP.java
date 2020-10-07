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
        richPresence.largeImageText = "Mamong us";
        richPresence.partyMax = 0;
        switch (Main.lastState) {
            case LOADING -> {
                richPresence.details = "Waiting";
                richPresence.state = "Connecting with "+ip;
            }
            case MULTIPLAYER -> {
                richPresence.details = "Playing multiplayer";
                richPresence.state = "On "+ip;
            }
            case SINGLEPLAYER -> {
                richPresence.details = "Playing singleplayer";
                richPresence.state = "Playing alone like a pussy";
            }
            default -> {
                richPresence.details = "Not Playing";
                richPresence.state = "In the main menu";
            }
        }
        richPresence.startTimestamp = created;
        DiscordRPC.discordUpdatePresence(richPresence);
    }
}

package net.runelite.client.plugins.clanchattracker;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.clan.ClanChannel;
import net.runelite.api.clan.ClanChannelMember;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@PluginDescriptor(
        name = "Clan Chat Tracker",
        description = "Tracks when users enter and leave clan chats and logs the information.",
        tags = {"clan", "chat", "tracker", "logging"}
)
public class ClanChatTrackerPlugin extends Plugin
{
    @Inject
    private ClanChatTrackerConfig config;

    private final Map<String, List<ActivityLog>> activityLogs = new HashMap<>();

    @Provides
    ClanChatTrackerConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ClanChatTrackerConfig.class);
    }

    @Override
    protected void startUp()
    {
        activityLogs.clear();
        log.info("Clan Chat Tracker started!");
    }

    @Override
    protected void shutDown()
    {
        activityLogs.clear();
        log.info("Clan Chat Tracker stopped!");
    }

    @Subscribe
    public void onClanMemberJoined(net.runelite.api.events.ClanMemberJoined event)
    {
        ClanChannelMember member = event.getClanMember();
        ClanChannel clanChannel = event.getClanChannel();
        logActivity(member.getName(), "JOINED", clanChannel.getName());
    }

    @Subscribe
    public void onClanMemberLeft(net.runelite.api.events.ClanMemberLeft event)
    {
        ClanChannelMember member = event.getClanMember();
        ClanChannel clanChannel = event.getClanChannel();
        logActivity(member.getName(), "LEFT", clanChannel.getName());
    }

    private void logActivity(String username, String action, String clanName)
    {
        if (username == null || clanName == null)
        {
            return;
        }

        ActivityLog logEntry = new ActivityLog(username, LocalDateTime.now(), action, clanName);

        activityLogs.computeIfAbsent(clanName, k -> new ArrayList<>()).add(logEntry);

        if (config.loggingEnabled())
        {
            log.info("User {} {} clan chat: {}", username, action, clanName);
        }
    }

    public List<ActivityLog> getLogsForClan(String clanName)
    {
        return activityLogs.getOrDefault(clanName, new ArrayList<>());
    }

    public void exportLogsToFile(String filePath)
    {
        // Implementation for exporting logs to a file (optional).
    }
}

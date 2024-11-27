// File: src/main/java/net/runelite/client/plugins/clanchattracker/ActivityLog.java

package net.runelite.client.plugins.clanchattracker;

import java.time.LocalDateTime;

public class ActivityLog
{
    private final String username;
    private final LocalDateTime timestamp;
    private final String action;
    private final String clanName;

    public ActivityLog(String username, LocalDateTime timestamp, String action, String clanName)
    {
        this.username = username;
        this.timestamp = timestamp;
        this.action = action;
        this.clanName = clanName;
    }

    public String getUsername()
    {
        return username;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public String getAction()
    {
        return action;
    }

    public String getClanName()
    {
        return clanName;
    }

    @Override
    public String toString()
    {
        return String.format("[%s] %s %s clan chat %s", timestamp, username, action, clanName);
    }
}

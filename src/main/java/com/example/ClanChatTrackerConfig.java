// File: src/main/java/net/runelite/client/plugins/clanchattracker/ClanChatTrackerConfig.java

package net.runelite.client.plugins.clanchattracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ClanChatTracker")
public interface ClanChatTrackerConfig extends Config
{
    @ConfigItem(
            keyName = "loggingEnabled",
            name = "Enable Logging",
            description = "Enable or disable logging of clan chat activities"
    )
    default boolean loggingEnabled()
    {
        return true;
    }
}

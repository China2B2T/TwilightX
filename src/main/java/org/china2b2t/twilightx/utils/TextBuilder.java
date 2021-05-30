package org.china2b2t.twilightx.utils;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class TextBuilder {

    private static Class<?> _CRAFTPLAYER_CLASS;
    private static Constructor<?> _PACKET_PLAY_OUT_CHAT_CONSTRUCTOR;
    private static Method _A_METHOD, _GETHANDLE_METHOD, _SEND_PACKET_METHOD;
    private static Field _PLAYER_CONNECTION_FIELD;

    static {
        String name = Bukkit.getServer().getClass().getName();
        name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
        final String _VERSION = name.substring(0, name.indexOf("."));
        try {
            Class<?> _ICHAT_BASE_COMPONENT_CLASS = Class
                    .forName("net.minecraft.server." + _VERSION + ".IChatBaseComponent");
            Class<?> _PACKET_PLAY_OUT_CHAT_CLASS = Class
                    .forName("net.minecraft.server." + _VERSION + ".PacketPlayOutChat");
            _CRAFTPLAYER_CLASS = Class.forName("org.bukkit.craftbukkit." + _VERSION + ".entity.CraftPlayer");
            Class<?> _ENTITYPLAYER_CLASS = Class.forName("net.minecraft.server." + _VERSION + ".EntityPlayer");
            Class<?> _PLAYER_CONNECTION_CLASS = Class.forName("net.minecraft.server." + _VERSION + ".PlayerConnection");

            _PACKET_PLAY_OUT_CHAT_CONSTRUCTOR = _PACKET_PLAY_OUT_CHAT_CLASS
                    .getConstructor(_ICHAT_BASE_COMPONENT_CLASS, byte.class);

            _A_METHOD = Class.forName("net.minecraft.server." + _VERSION + ".IChatBaseComponent$ChatSerializer")
                    .getMethod("a", String.class);
            _GETHANDLE_METHOD = _CRAFTPLAYER_CLASS.getMethod("getHandle");
            _SEND_PACKET_METHOD = _PLAYER_CONNECTION_CLASS.getMethod("sendPacket",
                    Class.forName("net.minecraft.server." + _VERSION + ".Packet"));

            _PLAYER_CONNECTION_FIELD = _ENTITYPLAYER_CLASS.getDeclaredField("playerConnection");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // Main message text
    private static String text;

    // Value of the hover events
    private String hover;

    // Value of the click events
    private String click;

    /**
     * Hover event type
     *
     * @see HoverEventType
     */
    private HoverEventType hoverAction;

    /**
     * Click event type
     *
     * @see ClickEventType
     */
    private ClickEventType clickAction;

    /**
     * @param text Main message text
     */
    public TextBuilder(String text) {
        TextBuilder.text = text;
    }

    // Basic JSON message, without any events
    private static String json = "{\"text\":\"" + text + "\"}";

    /**
     * @param type  Type of the event (show text, show achievement, etc.)
     * @param value Value of the text inside it
     */
    public TextBuilder setHoverEvent(TextBuilder.HoverEventType type, String value) {
        Validate.notNull(type, "HoverActionType cannot be null");
        Validate.notNull(value, "JSON Value cannot be null");
        hover = value;
        hoverAction = type;
        return this;
    }

    /**
     * @param type  Type of the event (suggest command, open URL, etc.)
     * @param value Value of the event
     */
    public TextBuilder setClickEvent(TextBuilder.ClickEventType type, String value) {
        Validate.notNull(type, "ClickActionType cannot be null");
        Validate.notNull(value, "JSON Value cannot be null");
        click = value;
        clickAction = type;
        return this;
    }

    public TextBuilder buildText() {
        if (!getClick().isPresent() && !getHover().isPresent()) {
            json = "{\"text\":\"" + text + "\"}";
        }
        if (!getClick().isPresent() && getHover().isPresent()) {
            if (hoverAction == HoverEventType.SHOW_ACHIEVEMENT) {
                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"achievement." + hover + "\"}}";
            } else if (hoverAction == HoverEventType.SHOW_STATISTIC) {
                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"stat." + hover + "\"}}";
            } else {
                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"" + hover + "\"}}";
            }
        }
        if (getClick().isPresent() && getHover().isPresent()) {
            json = "{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"" + clickAction.getActionName() + "\",\"value\":\"" + click + "\"},\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"" + hover + "\"}}";
        }
        if (getClick().isPresent() && !getHover().isPresent()) {
            json = "{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"" + clickAction.getActionName() + "\",\"value\":\"" + click + "\"}}";
        }
        return this;
    }

    /**
     * @param player Send the player the modified text in the builder
     */
    public void sendMessage(Player player) {
        try {
            Object messageComponent = _A_METHOD.invoke(null, json);
            Object packet = _PACKET_PLAY_OUT_CHAT_CONSTRUCTOR.newInstance(messageComponent, (byte) 1);
            Object craftPlayer = _CRAFTPLAYER_CLASS.cast(player);
            Object entityPlayer = _GETHANDLE_METHOD.invoke(craftPlayer);
            Object playerConnection = _PLAYER_CONNECTION_FIELD.get(entityPlayer);
            _SEND_PACKET_METHOD.invoke(playerConnection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return The original message text, without any formatting
     */
    public String getUnformattedText() {
        return text;
    }

    /**
     * @return The JSON syntax, after all the modifying.
     */
    public String getJson() {
        return json;
    }

    /**
     * @return Optional of the hover value (to simplify null checks)
     */
    private Optional<String> getHover() {
        return Optional.of(hover);
    }

    /**
     * @return Optional of the click value (to simplify null checks)
     */
    private Optional<String> getClick() {
        return Optional.of(click);
    }

    /**
     * Click events
     */
    public enum ClickEventType {

        /**
         * Open a URL on click
         */
        OPEN_URL("open_url"),

        /**
         * Force the player to run a command on click
         */
        RUN_COMMAND("run_command"),

        /**
         * Add text into the player's chat field
         */
        SUGGEST_TEXT("suggest_command");

        // JSON name of the events
        private String actionName;

        ClickEventType(String actionName) {
            this.actionName = actionName;
        }

        /**
         * @return JSON name of the event
         */
        public String getActionName() {
            return actionName;
        }
    }

    public enum HoverEventType {

        /**
         * Show text on hover
         */
        SHOW_TEXT("show_text"),

        /**
         * Show an item information on hover
         */
        SHOW_ITEM("show_item"),

        /**
         * Show an achievement on hover
         */
        SHOW_ACHIEVEMENT("show_achievement"),

        /**
         * Show a statistic on hover (the same action name is due to them being the same, however the prefix is different)
         * Since achievements take achievement.AchievementName, while statistics take stat.StatisticName
         */
        SHOW_STATISTIC("show_achievement");

        // JSON name of the event
        private String actionName;

        HoverEventType(String actionName) {
            this.actionName = actionName;
        }

        /**
         * @return JSON name of the event
         */
        public String getActionName() {
            return actionName;
        }
    }
}

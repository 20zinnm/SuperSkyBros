package com.relicum.scb.utils;

/**
 * Player can either be in, Lobby or Game, or UNKNOWN Not interested in UNKNOWN
 *
 * @author Relicum
 * @version 0.1
 */
public enum PlayerSt {
    LOBBY,
    GAME,
    UNKNOWN,
    OFFLINE,
    JOINEDSERVER,
    ADMINMODE;

    public static PlayerSt find(String abbr) {
        for (PlayerSt v : values()) {
            if (v.equals(abbr)) {
                return v;
            }
        }
        return null;
    }
}

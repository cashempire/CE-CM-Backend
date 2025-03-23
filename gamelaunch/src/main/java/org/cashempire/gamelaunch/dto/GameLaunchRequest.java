package org.cashempire.gamelaunch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameLaunchRequest {
    private String playerId;
    private String gameId;
    private String agentId;
    private String agentKey;
    private String sessionKey;
    private String deviceType;
    private String currency;
    private boolean isSound;
    private String country;
    private String language;
    private boolean isLogo;
}

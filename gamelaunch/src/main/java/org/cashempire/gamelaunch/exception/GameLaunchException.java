package org.cashempire.gamelaunch.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameLaunchException extends Exception {

    private String message;

    public GameLaunchException() {
    }
    public GameLaunchException(String message) {
        this.message = message;
    }


}

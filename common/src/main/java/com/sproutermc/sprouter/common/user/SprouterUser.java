package com.sproutermc.sprouter.common.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SprouterUser {

    private final String name;

    public abstract void sendMessage(String message);
}

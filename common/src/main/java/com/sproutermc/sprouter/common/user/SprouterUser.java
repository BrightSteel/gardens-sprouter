package com.sproutermc.sprouter.common.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SprouterUser {
    protected final String uuid; // identification purposes; non-players should have pre-defined uuids
    protected final String displayName;
}

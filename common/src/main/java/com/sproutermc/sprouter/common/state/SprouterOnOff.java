package com.sproutermc.sprouter.common.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SprouterOnOff {

    ON(true), OFF(false);

    private final boolean isOn;
}



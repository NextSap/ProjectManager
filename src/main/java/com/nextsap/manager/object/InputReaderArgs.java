package com.nextsap.manager.object;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputReaderArgs {
    private String[] args;
    private boolean error;
}

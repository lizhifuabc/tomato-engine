/*
 * Copyright 2022 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package com.tomato.engine.oshi.example.jmx.api;

import javax.management.MBeanServer;
import java.io.IOException;

public interface JMXOshiAgent extends MBeanServer {
    public void startAgent() throws IOException;

    public void stopAgent() throws IOException;

}

/*
 * Copyright 2022-2023 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package com.tomato.engine.oshi.example.jmx;

import com.tomato.engine.oshi.example.jmx.api.JMXOshiAgent;

import java.util.Map;


public class CreateJmxOshiAgent {
    private static ContextRegistrationPlatform platform = new ContextRegistrationPlatform();

    public static JMXOshiAgent createJmxOshiAgent(Integer port, String host) throws Exception {
        return JMXOshiAgentServer.getInstance(host, port, null, platform);
    }

    public static JMXOshiAgent createJmxOshiAgent(Integer port, String host, Map<String, ?> properties)
            throws Exception {
        return JMXOshiAgentServer.getInstance(host, port, properties, platform);
    }
}

/*
 * Copyright 2022 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package com.tomato.engine.oshi.example.jmx.demo;


import com.tomato.engine.oshi.example.jmx.CreateJmxOshiAgent;
import com.tomato.engine.oshi.example.jmx.api.JMXOshiAgent;

public class OshiJMXServer {
    public static void main(String[] args) throws Exception {

        JMXOshiAgent oshiAgent = CreateJmxOshiAgent.createJmxOshiAgent(8888, "127.0.0.1");
        oshiAgent.startAgent();
    }
}

package com.example.demo.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/*
@Configuration
public class RedisConfig {

	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;

	@Bean
	public JedisCluster getJedisCluster() {
		String[] nodes = clusterNodes.split(",");
		Set<HostAndPort> clusterNodes = new HashSet<>();
		for (String node : nodes) {
			String[] ipPort = node.split(":");

			clusterNodes.add(new HostAndPort(ipPort[0], Integer.parseInt(ipPort[1])));

		}
		JedisCluster cluster = new JedisCluster(clusterNodes);
		return cluster;
	}
}*/
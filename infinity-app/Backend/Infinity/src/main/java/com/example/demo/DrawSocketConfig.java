package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * **IMPORTANT**
 * Check the main application file as well!
 * This class configures your endpoints with the rest of the
 * Spring Application. It redirects the "ws://" requests to your endpoint.
 *
 */

@Configuration
public class DrawSocketConfig {
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}

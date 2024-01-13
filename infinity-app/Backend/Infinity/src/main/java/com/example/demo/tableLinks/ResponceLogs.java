package com.example.demo.tableLinks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponceLogs {

	private Logger classify;
	
	public enum Logger {
		IMMORTALUSER, USERALREADYEXISITS
	}

	public ResponceLogs(Logger classify) {
		this.classify = classify;
	}
	
	public ResponseEntity<String> ResponceLog(Logger log) {
		if (log.equals(Logger.IMMORTALUSER)) {
			return new ResponseEntity<String>("Immortal user detected! Make sure that the user is not the owner.",
					HttpStatus.OK);
		}
		if (log.equals(Logger.USERALREADYEXISITS)) {
			return new ResponseEntity<String>("User detected! Make sure that your not adding the same user.",
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("No vaild Logs detected", HttpStatus.OK);
	}
}
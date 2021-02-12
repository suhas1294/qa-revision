package com.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
		private String testEnv;
		private String browserMode;
		private int timeoutForWait;
		private int pollingDuration;
		private int explicitTimeOut;
}

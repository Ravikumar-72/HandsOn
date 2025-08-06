package com.project.FinWallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.project.FinWallet.entity")
public class FinWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinWalletApplication.class, args);
	}

}

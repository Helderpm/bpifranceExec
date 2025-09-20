package com.bpifranceexec.exec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan({
        "com.bpifranceexec.exec.domaine",
        "com.bpifranceexec.exec.infrastructure.repository",
        "com.bpifranceexec.exec.exposition.mapper",
        "com.bpifranceexec.exec.exposition",
        "com.bpifranceexec.exec.exposition.mapper"
})
public class BpiExecApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpiExecApplication.class, args);
	}

}

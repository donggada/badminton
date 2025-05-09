plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.toy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}


repositories {
	mavenCentral()
}

val querydslVersion = "5.0.0"
val querydslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// QueryDSL
	implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	// 기타
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("com.h2database:h2")
}

// 소스 세트 설정
sourceSets {
	main {
		java {
			srcDir(querydslDir)
		}
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.annotationProcessorPath = configurations.annotationProcessor.get()
	options.generatedSourceOutputDirectory.set(file(querydslDir))
}

tasks.named<Delete>("clean") {
	delete(file(querydslDir))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
.PHONY: build
build:
	./gradlew shadowJar

run: build
	docker-compose down
	docker-compose up || true

eclipse:
	./gradlew eclipse

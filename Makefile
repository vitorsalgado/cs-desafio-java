SHELL := /bin/bash

build:
	clear
	./gradlew clean
	./gradlew build

run:
	clear
	./gradlew run

infer:
	clear
	./gradlew clean
	infer run -- ./gradlew build

.PHONY: build

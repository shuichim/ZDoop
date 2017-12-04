num?=10
filename=tools/docker-compose-$(num).yml

.PHONY: build
build:
	./gradlew shadowJar

run: build
	docker-compose down
	docker-compose up || true

eclipse:
	./gradlew eclipse

master:
	java -cp build/libs/ZDoop-all.jar org.iit.zserver.ZMaster m1 &
	sleep 1

workers:
	@number=1; while [[ $$number -le $(num) ]] ; do \
		echo $$number ; \
		java -cp build/libs/ZDoop-all.jar org.iit.zworker.ZWorker w$$number & \
		((number = number + 1)) ; \
	done
	sleep 3

client:
	java -cp build/libs/ZDoop-all.jar org.iit.zclient.Client -f build/resources/main/1mb.txt || true

kill:
		pkill -f ZDoop-all.jar || true

exp:
	docker-compose -f $(filename) down
	docker-compose -f $(filename) up || true

clean: build
	java -cp build/libs/ZDoop-all.jar util.Main
	sleep 1

exp2: kill clean master workers client

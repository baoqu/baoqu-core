docker:
	@echo ">> Cleaning dist files"
	rm -rf tartget front-dist
	@echo ">> Building Baoqu front"
	(cd ../baoqu-front && make dist)
	mv ../baoqu-front/dist front-dist
	@echo ">> Building Baoqu back"
	rm -rf target
	lein uberjar
	@echo ">> Building docker image"
	docker build -t baoqu .

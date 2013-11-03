clean:
	-rm -r build
	-mkdir build

dropzone:
	javac -d build src/*.java

test: dropzone
	java -cp build DropZone < input/sample.in

judge: dropzone
	java -cp build DropZone < input/judges.in


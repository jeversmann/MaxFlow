clean:
	-rm -r build
	-mkdir build

dropzone:
	javac -d build src/*.java

sample: dropzone
	-rm output/sample.out
	java -cp build DropZone < input/sample.in >> output/sample.out
	diff output/sample.out output/sample.ok

judge: dropzone
	-rm output/judges.out
	java -cp build DropZone < input/judges.in >> output/judges.out
	diff output/judges.out output/judges.ok


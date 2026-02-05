CC = javac
JAVAFILES = src/com/app/*.java
MAIN = com.app.Main
CLASSDIR = class
JAR = game.jar
DOC = doc

.PHONY: all run clean

all: $(CLASSDIR)
	$(CC) -d $< $(JAVAFILES)

$(CLASSDIR):
	mkdir -p $@

run: all
	java -cp $(CLASSDIR) $(MAIN) $(PARAM)

jar: all
	jar cfe $(JAR) $(MAIN) -C $(CLASSDIR) .

runJar: jar
	java -jar $(JAR) $(PARAM)

$(DOC) : $(JAVAFILES)
	@ javadoc -d $@ -author -encoding UTF-8 $(JAVAFILES)

clean:
	rm -rf $(CLASSDIR) $(DOC) $(JAR)

CC = javac
MAIN = com.app.Main
SRCDIR = src
TARGET = src/com/app/Main.java
CLASSDIR = bin
JAR = game.jar
DOC = doc

.PHONY: all run clean

all: $(CLASSDIR)
	$(CC) -d $< -sourcepath $(SRCDIR) $(TARGET)

$(CLASSDIR):
	mkdir -p $@

run: all
	java -cp $(CLASSDIR) $(MAIN) $(PARAM)

jar: all
	jar cfe $(JAR) $(MAIN) -C $(CLASSDIR) .

runJar: jar
	java -jar $(JAR) $(PARAM)

$(DOC) : $(JAVAFILES)
	@ javadoc -d $@ -author -encoding UTF-8 $(find $(SRCDIR) -name "*.java")

clean:
	rm -rf $(CLASSDIR) $(DOC) $(JAR)

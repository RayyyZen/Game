CC = javac
MAIN = com.app.Main
SRCDIR = src
PATHPACKAGES = com.app
TARGET = $(JAVAFILESPATH)/Main.java
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

$(DOC):
	@ javadoc -d $@ -author -encoding UTF-8 -sourcepath $(SRCDIR) -subpackages $(PATHPACKAGES)

clean:
	rm -rf $(CLASSDIR) $(DOC) $(JAR)

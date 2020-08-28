JAVAC=/usr/bin/javac

.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOC=./doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=FindBasin.class Main.class

CLASSE=FindBasin.java Main.java
 
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)
SOURCEFILES=$(CLASSE:%.java=$(SRCDIR)/%.java)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class
par:
	java -cp $(BINDIR) Main "small_in.txt" "out_small.txt"
	java -cp $(BINDIR) Main "med_in.txt" "out_med.txt"
	java -cp $(BINDIR) Main "large_in.txt" "out_large.txt"
	java -cp $(BINDIR) Main "trivialbasin_in.txt" "out_trivialbasin.txt"
	java -cp $(BINDIR) Main "trivialhill_in.txt" "out_trivialhill.txt"

doc:	$(CLASS_FILES)
	javadoc  -d $(DOC) $(SOURCEFILES)

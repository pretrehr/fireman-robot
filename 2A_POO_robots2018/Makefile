# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: TestChefPompier

TestChefPompier:
	javac -d bin -classpath bin:bin/gui.jar -sourcepath src src/test/TestChefPompier.java

# Execution:
# Faire make exesujet

exesujet:
	java -classpath bin:bin/gui.jar test/TestChefPompier bin/io/cartes/carteSujet.map -Xmx1024m
exedesert:
	java -classpath bin:bin/gui.jar test/TestChefPompier bin/io/cartes/desertOfDeath-20x20.map
exemushroom:
	java -classpath bin:bin/gui.jar test/TestChefPompier bin/io/cartes/mushroomOfHell-20x20.map
exespiral:
	java -classpath bin:bin/gui.jar test/TestChefPompier bin/io/cartes/spiralOfMadness-50x50.map -Xmx1024m

clean:
		rm -rf bin/*.class
		rm -rf bin/carte/*.class
		rm -rf bin/cheminoptimal/*.class
		rm -rf bin/graphicinterface/*.class
		rm -rf bin/io/*.class
		rm -rf bin/robot/*.class
		rm -rf bin/simulateur/*.class
		rm -rf bin/test/*.class

@echo off
javac -d "C:\Users\%USERNAME%\IdeaProjects\Yahtzee\out\production\Yahtzee" *.java
java -classpath "C:\Users\%USERNAME%\IdeaProjects\Yahtzee\out\production\Yahtzee" com.ernstye.main.Yahtzee
cmd /k
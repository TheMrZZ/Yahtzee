@echo off
javac -d %cd% *.java
java -classpath %cd% com.ernstye.main.Yahtzee
cmd /k
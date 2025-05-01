@echo off
echo Compiling everything.
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/*.java
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/scripts/*.java
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/scripts/dialogues/*.java
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/scripts/interfaces/*.java
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/scripts/items/*.java
"C:\Program Files\Java\jdk1.7.0_21\bin\javac.exe" -d bin -cp lib/*; -sourcepath src src/dragonkk/rs2rsps/scripts/objects/*.java
pause


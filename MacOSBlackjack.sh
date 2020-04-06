#!/bin/bash

javac -d bin src/blackjack/*.java
if [ ! -f "1C.png" ]
then
cp resources/*.png bin
fi
cd bin
java -cp ".:.." blackjack.Blackjack

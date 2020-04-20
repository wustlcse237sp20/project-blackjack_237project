javac -d bin src/blackjack/*.java
cd bin
if not exist "1C.png" xcopy "../resources"
java -cp ".;resources" blackjack.Blackjack
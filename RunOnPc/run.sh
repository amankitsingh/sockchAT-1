mkdir build
javac -d ./build -cp ./src ./src/Client.java
javac -d ./build -cp ./src ./src/Server.java
javac -d ./build -cp ./src ./src/ChatProgram.java
java -cp build/ ChatProgram



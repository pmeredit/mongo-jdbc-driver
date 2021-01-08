# Instructions

From the root dir of this git repo, run `./gradlew :debug:run --args '<jdbc uri> <query>'`

Notes:
- A JDBC URI is a MongoDB URI prefixed with `jdbc:`
- The gradle target will build the program if it isn't cached, so make sure you don't measure timing the first time around

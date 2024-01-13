## MODIFICATIONS:

- BACKEND: change "directory" variable in TriviaService.java, make sure there is a '/' in the end

- FRONTEND: change "url" variables in both activities if necessary


## HOW TO RUN A SIMPLE ROUNDTRIP：

1. run the SpringBoot app
2. run the Android app
3. upload an image from Android in the Upload activity
4. open Postman, check all trivias at http://localhost:8080/trivia/all (replace localhost if necessary)
5. in Android app, back to the main activity, replace the 'X' in the url with an existing trivia id
6. hit 'GET' to load the picture from server

## NOTE: backend is based on H2 database currently
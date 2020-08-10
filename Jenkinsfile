pipeline {
    agent any

    stages {
        stage("Java tests") {
            steps {
                echo "Start maven tests"
                bat "mvn test"
            }
        }
        stage("Build") {
            steps {
                echo "Start build"
                sh "mvn -DskipTests -B clean package"
            }
        }
        stage("Deploy") {
            steps {
                echo "Start deploy"
                sh "docker build -t game-library-api:latest ./docker/backend"
                sh "docker-compose --file ./docker/docker-compose.yml up --detach"
            }
        }
        stage("Newman tests") {
            steps {
                sh '''#!/bin/bash
                    correctCode=200;
                    counter=0;
                    numberOfAttemps=5;
                    secondsToSleep=5;

                    while [ $counter -le $numberOfAttemps ];
                    do
                        responseCode=$(curl --write-out '%{http_code}' --silent --output /dev/null http://localhost:8000/games);
                        if [ $responseCode -eq $correctCode ]; then
                            echo "Running newman tests"
                            newman run ./newman/tests.json -e ./newman/environment.json --disable-unicode
                        elif [ "$counter" -lt "$numberOfAttemps" ]; then
                            echo "Will try to reconnect after $secondsToSleep seconds";
                            sleep $secondsToSleep;
                            ((counter++));
                        else
                            exit 1;
                        fi
                    done;
                '''
            }
        }
    }
}
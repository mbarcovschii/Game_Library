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
                bat "mvn clean package -DskipTests"
            }
        }
        stage("Deploy") {
            steps {
                echo "Start deploy"
                bat "docker build -t game-library-api:latest ./docker/backend"
                bat "docker-compose --file ./docker/docker-compose.yml up --detach"
            }
        }
        stage("Newman tests") {
            steps {
                sh '''#!/bin/bash
                    correct_code="200";
                    counter=0;
                    numberOfAttemps=5;
                    secondsToSleep=5;

                    while [ $counter -le $numberOfAttemps ];
                    do
                    	response_code=$(curl --write-out '%{http_code}' --silent --output /dev/null http://localhost:8000/games);
                    	if [ "$response_code" = "$correct_code" ]; then
                    		echo "Running newman tests"
                    		C:\\Users\\mbarcovschii\\AppData\\Roaming\\npm\\newman.cmd run ./newman/tests.json -e ./newman/environment.json --disable-unicode
                    		exit 0;
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
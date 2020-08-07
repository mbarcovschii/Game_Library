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
                echo "Start newman tests"
                bat "C:\\Users\\mbarcovschii\\AppData\\Roaming\\npm\\newman.cmd run ./newman/tests.json -e ./newman/environment.json --disable-unicode"
            }
        }
    }
}
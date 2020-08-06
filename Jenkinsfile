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
                bat "docker-compose up --detach"
            }
        }
    }
}
pipeline {
    agent any
    stages {
        stage("Java tests") {
            steps {
                echo "Starting maven tests"
                bat "mvn test"
            }
        }
        stage("Build") {
            steps {
                echo "Starting build"
                bat "mvn clean package -DskipTests"
            }
        }
        stage("Deploy") {
            steps {
                echo "Starting deploy"
            }
        }
    }
}
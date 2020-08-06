pipeline {
    agent any
    stages {
        stage("Test") {
            steps {
                echo "Starting maven tests"
                bat "mvn test"
            }
        }
    }
}
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
                script {
                    def responseCode = sh returnStdout: true,
                    script: "url --write-out '%{http_code}' --silent --output /dev/null http://localhost:8000/games"
                    echo responseCode;
                }
            }
        }
    }
}
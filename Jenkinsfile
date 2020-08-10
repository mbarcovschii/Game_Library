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
                echo "Start newman tests"
                timeout(time: 20, unit: 'SECONDS') {
                    waitUntil {
                        script {
                            def result =
                                sh script: "curl --write-out '%{http_code}' --silent --output /dev/null http://localhost:8000/games",
                                returnStdout: true
                            return (result == "200")
                        }
                    }
                }
                sh "newman run ./newman/tests.json -e ./newman/environment.json --disable-unicode"
            }
        }
    }
}
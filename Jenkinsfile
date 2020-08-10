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
                script {
                    for (int i = 0; i < 5; i++) {
                        if (i == 5) {
                            sh "exit 1"
                        } else {
                            def responseCode = sh returnStdout: true,
                            script: "curl --write-out '%{http_code}' --silent --output /dev/null http://localhost:8000/games"

                            if (responseCode == 200) {
                                sh "newman run ./newman/tests.json -e ./newman/environment.json --disable-unicode"
                                sh "exit 0"
                            } else {
                                echo "Retry to send request after 5 seconds"
                                sleep(5000)
                            }
                        }
                    }

                }
            }
        }
    }
}
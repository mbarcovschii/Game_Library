pipeline {
    agent any

    stages {
        stage("Prepare environment variables") {
            steps {
                script {
                    env.appName = readMavenPom().getArtifactId()
                    env.appVersion = readMavenPom().getVersion()
                    echo "App name: ${appName} \nApp version: ${appVersion}"
                }
            }
        }
        stage("Java Tests") {
            steps {
                echo "Java Tests"
                sh "mvn test"
            }
        }
        stage("Build Java Artifact") {
            steps {
                echo "Building Java Artifact"
                sh "mvn -DskipTests -B clean package"
            }
        }
        stage("Build docker images") {
            steps {
                echo "Build docker image and pushing it on DockerHub"
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploy"
                sh "docker-compose --file ./docker/docker-compose.yml up --detach"
            }
        }
        stage("Newman Tests") {
            steps {
                echo "Start Newman Tests"
                timeout(time: 20, unit: 'SECONDS') {
                    waitUntil {
                        script {
                            def result = "000"
                            result =
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
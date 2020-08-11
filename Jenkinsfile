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
                 withCredentials([usernamePassword(credentialsId: 'dockerCredentials', usernameVariable: "dockerLogin",
                    passwordVariable: "dockerPassword")]) {
                        sh """
                            docker login -u ${dockerLogin} -p ${dockerPassword}
                            docker build \
                            -t ${dockerLogin}/${appName}:${appVersion} \
                            -t ${dockerLogin}/${appName}:latest ./docker/backend
                            docker push ${dockerLogin}/${appName}:${appVersion}
                            docker push ${dockerLogin}/${appName}:latest
                        """
                    }
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
                timeout(time: 60, unit: 'SECONDS') {
                    waitUntil(initialRecurrencePeriod: 2000) {
                        script {
                            def result =
                                sh script: "curl --silent --output /dev/null http://localhost:8000/games",
                                returnStatus: true
                            return (result == 0)
                        }
                    }
                }
                sh "newman run ./newman/tests.json -e ./newman/environment.json --disable-unicode"
            }
        }
    }
}
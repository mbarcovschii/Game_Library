pipeline {
    agent { docker {
                    image 'maven:3-alpine'
                    label 'my-defined-label'
                    args  '-v /tmp:/tmp'
                }
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
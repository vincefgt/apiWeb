pipeline {
 agent any
    stages {
        stage('Clean Worksapce') {
        steps {
            cleanWs()
        }
        }
        stage('Git Checkout') {
            steps {
                script {
                    checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/vincefgt/apiWeb.git']])
                }
            }
        }
        stage('Build Maven') {
            steps {
              withMaven {
                  sh "mvn clean verify"
              }
            }
        }
    }
}

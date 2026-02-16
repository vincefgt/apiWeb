pipeline {
 agent any
 tools {
  maven 'Maven3'
  jdk 'JDK21'
}
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
            withMaven{
                bat "mvn clean verify"
                }
            }
        }
        stage('Allure-Report') {
            steps {
                allure includeProperties: false, jdk: '', resultPolicy: 'LEAVE_AS_IS', results: [[path: 'allure-results']]
            }
        }
        stage('Generate Allure Report'){
            steps {
                bat 'mvn allure:report'
            }
        }
    }

    post {
        always {
            allure ([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [('target/allure-results')]
            ])
        }
    }
}
#98527144ab3414bf72c3c341b4b055e412389d2c
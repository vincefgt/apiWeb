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
        stage('SonarCloub') {
              environment {
                     SONAR_TOKEN = credentials('sonarClod_token')
                 }
                 steps {
                     withSonarQubeEnv('SonarCloud') {
                         sh '''
                         mvn clean verify sonar:sonar \
                           -Dsonar.projectKey=apiWeb \
                           -Dsonar.organization=Vince-fgt \
                           -Dsonar.host.url=https://sonarcloud.io \
                           -Dsonar.login=$SONAR_TOKEN
                         '''
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
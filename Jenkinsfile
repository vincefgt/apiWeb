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
                scannerHome = tool 'sonarScanner8'
             }
             steps {
               withSonarQubeEnv('sonar-server') {
                  sh '''${scannerHome}/bin/sonar-scanner \
                      -Dsonar.projectKey=apiWeb \
                      -Dsonar.projectName=apiWeb \
                      -Dsonar.projectVersion=1.0 \
                      -Dsonar.sources=src/ \
                      -Dsonar.java.binaries=target/test-classes/com/visualpathit/account/controllerTest/ \
                      -Dsonar.junit.reportsPath=target/surefire-reports/ \
                      -Dsonar.jacoco.reportsPath=target/jacoco.exec \
                      -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml \
                      -Dsonar.organization=Vince.fgt'''
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
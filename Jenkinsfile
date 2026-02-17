pipeline {
 agent any
 environment {
     registryCredential = 'dockerhub_credential' // Jenkins credential ID
 }
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
            withMaven(maven: 'Maven3'){
                bat "mvn clean verify"
                }
            }
        }
        stage('SonarCloud') {
            steps {
                withSonarQubeEnv('Sonar-server') {
                    bat 'mvn sonar:sonar'
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
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('vincefgt/web:latest','-f Dockerfile .')
                }
            }
        }
        stage('Push DockerImage') {
            steps {
                script {
                    docker.withRegistry('',registryCredential) {
                        docker.image('vincefgt/web:latest').push()
                    }
                }
            }
        }
        stage('Deploy docker-compose'){
            steps {
                script {
                    bat 'docker-compose up -d --build --force-recreate --remove-orphans'
                }
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
                results: [('/allure-results')]
            ])
        }
    }
}
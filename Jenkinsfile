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
        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
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
    stage('Build Docker Image') {
        steps {
            script {
                docker.build('vincefgt/web:latest','-f Dockerfile .')
            }
        }
    }
    stage('Push DOckerImage') {
        steps {
            script {
                docker.withRegistry('',registryCredential) {
                    docker.image('vincefgt/web:latest').push()
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
                results: [('target/allure-results')]
            ])
        }
    }
}
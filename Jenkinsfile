pipeline {
    agent any
    tools{
        maven 'maven_3_8_1'
    }
    stages{
        stage('Build maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/braulinho96/backendProyecto']])
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t braulinho/tingesobackend:latest .'
                }
            }
        }
        stage('Push image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerCredentials', usernameVariable: 'dhpUser', passwordVariable: 'dhpsw')]) {
                        sh 'docker login -u $dhpUser -p $dhpsw'
                    }
                    sh 'docker push braulinho/tingesobackend:latest'
                }
            }
        }

    }
}
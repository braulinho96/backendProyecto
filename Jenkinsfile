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
        stage('Push image to Docker Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: '96c86941-6323-4824-bffd-0187728a5366	', variable: 'dhpsw')]) {
                        sh 'docker login -u braulinho -p ${dhpsw}'
                   }
                   sh 'docker push braulinho/tingesobackend:latest'
                }
            }
        }
    }
}
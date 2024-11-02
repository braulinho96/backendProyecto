<<<<<<< HEAD
=======
pipeline {
    agent any
>>>>>>> origin/main
    tools{
        maven 'maven_3_8_1'
    }
    stages{
        stage('Build maven'){
            steps{
<<<<<<< HEAD
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/braulinho96/backendProyecto']])
=======
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/amigo1975/book-service']])
>>>>>>> origin/main
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
<<<<<<< HEAD
                sh 'mvn test'
=======
                // Run Maven 'test' phase. It compiles the test sources and runs the unit tests
                bat 'mvn test' // Use 'bat' for Windows agents or 'sh' for Unix/Linux agents
>>>>>>> origin/main
            }
        }

        stage('Build docker image'){
            steps{
                script{
<<<<<<< HEAD
                    bat 'docker build -t braulinho/tingesobackend:latest .'
=======
                    bat 'docker build -t mtisw/book_service:latest .'
>>>>>>> origin/main
                }
            }
        }
        stage('Push image to Docker Hub'){
            steps{
                script{
<<<<<<< HEAD
                   withCredentials([string(credentialsId: '96c86941-6323-4824-bffd-0187728a5366	', variable: 'dhpsw')]) {
                        bat 'docker login -u braulinho -p %dhpsw%'
                   }
                   bat 'docker push braulinho/tingesobackend:latest'
=======
                   withCredentials([string(credentialsId: 'dhpswid', variable: 'dhpsw')]) {
                        bat 'docker login -u mtisw -p %dhpsw%'
                   }
                   bat 'docker push mtisw/book_service:latest'
>>>>>>> origin/main
                }
            }
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main

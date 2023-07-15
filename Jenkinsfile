pipeline{
    agent any
    tools{
        maven 'MyMaven'
        jdk 'MyJDK'
    }
    stages{
        stage('Git clone'){
            steps{
                git 'https://github.com/zorganimed/StockFonction.git'
            }
        }
        stage('Build'){
            steps{
                sh 'mvn package'
            }
        }
        stage('Create Dockerimage'){
            steps{
                sh 'docker build -t stock-jenkins-image .'
            }
        }
        
    }
    
}

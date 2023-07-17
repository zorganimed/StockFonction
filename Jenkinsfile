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
        stage('Docker image'){
            steps{
                sh 'docker build -t zorgani/stock-jenkins-image .'
            }
        }
       stage('Push image to HUb'){
            steps{ 
               withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                 sh 'docker login -u zorgani -p ${dockerhubpwd}'
                 
                 sh 'docker push zorgani/stock-jenkins-image'
             }
          }
        }
    }
    
}

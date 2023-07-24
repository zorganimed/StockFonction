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
               // checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/zorganimed/StockFonction']])
                sh 'mvn clean install -DskipTests'
            }
        }
         stage('Docker image'){
            steps{
                sh 'docker build -t zorgani/stk-jenkins-image .'
            }
        }
    stage('Push image to HUb'){
            steps{     
             withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                 sh 'docker login -u zorgani -p ${dockerhubpwd}'
                 
                 sh 'docker push zorgani/stk-jenkins-image'
              }
            }

        }
        
    }
    
}

pipeline {
    agent any
        stages{
        stage('Build') { 
            steps {
                sh 'mvn clean install -DskipTests' 
            }
        }
        stage('Test') { 
//             steps {
//                 sh 'mvn test' 
//             }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml' 
//                 }
//             }
        }
        stage ('Build Docker File')
        {
            steps {
                echo 'Starting to build docker image'
                echo 'this is agent change'
                sh 'docker build -t todo-roo:v3 .'     
    
            }
    //     sh docker build -t todo-roo:v3 .
        }
    }
}
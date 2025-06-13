pipeline {
    agent any
        stages{
        stage('Build') { 
            steps {
                sh 'mvn clean install -DskipTests -Dspring.profiles.active=docker' 
            }
        }
        stage('SonarQube analysis') {
            environment {
                scannerHome = tool 'Cube'
            }
            steps {
                withSonarQubeEnv('Cube') { // You can override the credential to be used
                        sh '''
                        echo "Starte SonarQube-Analyse..."
                        mvn sonar:sonar -X \
                            -Dsonar.projectKey=todo-roo \
                            -Dsonar.projectName='todo-roo'
                        echo "SonarQube Analyse abgeschlossen."
                        '''
                }
            }
        }
        stage('Test') { 
            steps {
                    echo 'imagine some epic testing!.'
//                 sh 'mvn test' 
            }
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
                sh 'docker save todo-roo > todo-roo.tar'
//                 sh 'scp todo-roo.tar'
//                 sh 'docker cp local-jenkins-aio2-jenkins-1:/todo-roo.tar .'
            }
    //     sh docker build -t todo-roo:v3 .
        }
    }
}
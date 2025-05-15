
node {
    stage('Build') { 
        steps {
            sh 'mvn -B -DskipTests clean package' 
    }
    stage('Test') { 
        steps {
            sh 'mvn test' 
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml' 
            }
        }
    }
    stage ('Build Docker File')
    {
        steps {
            echo 'Starting to build docker image'
//             script {
// //                 def customImage = docker.build("my-image:${env.BUILD_ID}")
// //                 customImage.push()
//             }
            
        }
//     sh docker build -t todo-roo:v3 .
    }
}
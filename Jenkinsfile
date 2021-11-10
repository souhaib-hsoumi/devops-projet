pipeline {

    agent any


    stages {
       stage ('GIT') {
            steps {
               echo "Getting Project from Git"; 
               git branch:"aymenbaghouli", url : "https://github.com/aymenbaghouli/projetdevops.git"; 
            }
        }

       stage("Build") {
           steps {
                bat "mvn -version"
                bat "mvn clean install -DskipTests"
            }
        }
        
    }
        stage("Sonar") {
            steps {
                bat "mvn sonar:sonar"
            }
        }
    
}
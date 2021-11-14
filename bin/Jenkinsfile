pipeline {

      agent any
      

 //   triggers {

   //     cron('*/5 * * * *')

     //     }

      stages {

  //  stage ('GIT') {
  //     steps {
    //     echo "Getting Project from Git"; 
     //   git branch:"aymenbaghouli", url : "https://github.com/aymenbaghouli/projetdevops.git"; 
    // }
//}

        stage("Verification de la verison du Maven") {
            steps {

                  bat "mvn -version"

                  }
        }

        stage("Supprimer le target ") {
            steps {
      
                bat "mvn clean"

                  }
        }

        stage("Build") {
            steps {

                bat "mvn compile"

                  }
        }

        stage("Lancement des tests unitaires ") {
            steps {

                bat "mvn test"
                  }
        }


      //  stage('Jacoco Build'){
      //   steps{
      //     step([$class: 'JacocoPublisher', 
      //     execPattern: 'target/*.exec',
      //     classPattern: 'target/classes',
      //     sourcePattern: 'src/main/java',
      //     exclusionPattern: 'src/test*'
      //      ])
        //  }
       // }

      //  stage("Sonar") {
      //    steps {

      //      bat "mvn sonar:sonar"
      //          }
      //  }

      //  stage("Deploiement avec Nexus") {
      //    steps {
      //      bat "mvn deploy:deploy-file -DgroupId=tn.esprit.spring -DartifactId=timesheet-ci -Dversion=9.4 -DgeneratePom=true -Dpackaging=jar -DrepositoryId=deploymentRepo -Durl=http://localhost:8081/repository/maven-releases/ -Dfile=target/timesheet-ci-9.4.jar"
       //         }

       // }       



    //  }

     // post {
     //     always {
     //       mail bcc: '', 
     //       body: '''Build completed successfully''', cc: '', from: '', replyTo: '', subject: 'Build successfull', to: 'tesnime.ammar@esprit.tn'
     //     }
     //   }



}
}
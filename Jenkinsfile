pipeline {
    agent any
  environment {

    registry = ""
    dockerContainerName = 'cat-bot'
    dockerImageName = 'cat-bot'
  }
  stages {

    stage('Build') {
       steps {
         sh "mvn clean install"
       }
    }
  stage('docker-compose start') {
      steps {
       sh 'docker compose up -d --build --force-recreate'
      }
    }
  }
}
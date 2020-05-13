pipeline {
  agent any
  stages {
    stage('prebuild') {
      steps {
        echo 'start build'
      }
    }

    stage('build') {
      steps {
        sh '../gradlew assembleDebug'
      }
    }

    stage('success') {
      parallel {
        stage('success') {
          steps {
            archiveArtifacts(excludes: 'apk', onlyIfSuccessful: true, artifacts: 'app')
          }
        }

        stage('failed') {
          steps {
            echo 'failed'
          }
        }

      }
    }

  }
}
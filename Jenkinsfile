pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
		stage('SCM Checkout'){
			steps{
			git url: 'https://github.com/ashishgit2018/demotest.git'
			}
		}
        stage('Compile Project') { 
            steps {
                sh 'mvn -B -f test-main -DskipTests clean install'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn install -PTestRunner -f test-main/test-auto'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') { 
            steps {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}
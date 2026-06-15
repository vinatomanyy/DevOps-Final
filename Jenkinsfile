pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                sh 'ansible-playbook -i inventory.ini playbook.yml'
            }
        }
    }

    post {
        failure {
            emailext(
                subject: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Build failed for ${env.JOB_NAME} #${env.BUILD_NUMBER}.

Committed by: ${env.GIT_AUTHOR_NAME}
Branch: ${env.GIT_BRANCH}
Check console output: ${env.BUILD_URL}
                """,
                to: 'srengty@gmail.com',
                cc: "${env.GIT_AUTHOR_EMAIL}"
            )
        }
        success {
            echo 'Build, test and deploy completed successfully.'
        }
    }
}

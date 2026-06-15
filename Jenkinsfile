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
            script {
                def commitEmail = sh(script: 'git log -1 --pretty=format:%ae', returnStdout: true).trim()
                def commitName = sh(script: 'git log -1 --pretty=format:%an', returnStdout: true).trim()
                emailext(
                    subject: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
Build failed for ${env.JOB_NAME} #${env.BUILD_NUMBER}.

Committed by: ${commitName}
Branch: ${env.GIT_BRANCH}
Check console output: ${env.BUILD_URL}
                    """,
                    to: "${commitEmail}, srengty@gmail.com"
                )
            }
        }
        success {
            echo 'Build, test and deploy completed successfully.'
        }
    }
}

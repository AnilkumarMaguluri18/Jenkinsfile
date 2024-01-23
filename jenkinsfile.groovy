pipeline {
    agent any

    environment {
        TF_HOME = tool 'Terraform'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    deleteDir()
                    checkout scm
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    dir('terraform') {
                        sh "${TF_HOME}/terraform init -backend-config='access_key=${TF_API_TOKEN}' -backend-config='secret_key=${TF_USERNAME}'"
                    }
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    dir('terraform') {
                        sh "${TF_HOME}/terraform plan -out=tfplan"
                    }
                }
            }
        }

        stage('Approval') {
            steps {
                script {
                    input 'Do you want to apply changes?'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    dir('terraform') {
                        sh "${TF_HOME}/terraform apply -auto-approve tfplan"
                    }
                }
            }
        }
    }
}

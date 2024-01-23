pipeline {
    agent any

    tools {
        terraform 'Terraform' // Make sure this matches the tool name configured in Jenkins
    }

    environment {
        TF_HOME = tool 'Terraform'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                // Checkout your source code from SCM (Git in this case)
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/AnilkumarMaguluri18/Jenkinsfile.git']]])
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    // Run Terraform init
                    sh "${TF_HOME}/terraform init -input=false"
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    // Run Terraform plan
                    sh "${TF_HOME}/terraform plan -out=tfplan -input=false"
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Run Terraform apply
                    sh "${TF_HOME}/terraform apply -input=false -auto-approve tfplan"
                }
            }
        }

        stage('Terraform Destroy') {
            steps {
                script {
                    // Run Terraform destroy
                    sh "${TF_HOME}/terraform destroy -input=false -auto-approve"
                }
            }
        }
    }
}

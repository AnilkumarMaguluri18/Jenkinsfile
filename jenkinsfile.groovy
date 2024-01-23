pipeline {
    agent any

    environment {
        TF_HOME = tool 'Terraform'
        TF_EXECUTABLE = "${TF_HOME}\\terraform"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                script {
                    // Your SCM checkout steps
                    checkout scm
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    // Run Terraform init
                    bat "\"${TF_EXECUTABLE}\" init -input=false"
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    // Run Terraform plan
                    bat "\"${TF_EXECUTABLE}\" plan -out=tfplan -input=false"
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Run Terraform apply
                    bat "\"${TF_EXECUTABLE}\" apply -input=false -auto-approve tfplan"
                }
            }
        }

        stage('Terraform Destroy') {
            steps {
                script {
                    // Run Terraform destroy
                    bat "\"${TF_EXECUTABLE}\" destroy -input=false -auto-approve"
                }
            }
        }
    }

    post {
        always {
            // Cleanup steps, if needed
            echo 'Always run cleanup steps here'
        }
    }
}

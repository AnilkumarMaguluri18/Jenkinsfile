pipeline {
    agent any

    stages {
        stage('Checkout Terraform Script') {
            steps {
                // Git clone your Terraform script repository
                git url: 'https://github.com/AnilkumarMaguluri18/terraform_file.git', branch: 'main'
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    // Change to the directory where your Terraform files are cloned
                    dir('terraform_file') {
                        // Run Terraform init
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Change to the directory where your Terraform files are cloned
                    dir('terraform_file') {
                        // Run Terraform apply
                        withAWS(region: 'us-east-2', credentials: 'my-aws-credentials') {
                            sh 'terraform apply -auto-approve'
                        }
                    }
                }
            }
        }
    }
}

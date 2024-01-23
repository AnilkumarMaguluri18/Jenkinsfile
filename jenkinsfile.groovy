pipeline {
    agent any

    stages {
        stage('Checkout Terraform Script') {
            steps {
                git url: 'https://github.com/AnilkumarMaguluri18/terraform_file.git', branch: 'main'
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    dir('terraform_file') {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    dir('terraform_file') {
                        // Use 'call' to invoke the appropriate command based on OS
                        bat(script: 'terraform apply -auto-approve -var-file=vpc_aws.tf', returnStatus: true) || sh 'terraform apply -auto-approve -var-file=vpc_aws.tf'
                    }
                }
            }
        }
    }
}

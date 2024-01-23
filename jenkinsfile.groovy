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
                        // Use double quotes around the entire sh command
                        sh "terraform apply -auto-approve -var-file=vpc_aws.tf"
                    }
                }
            }
        }
    }
}

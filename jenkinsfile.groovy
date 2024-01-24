pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID     = 'AKIAXIWTFG6ECV4CGZU7'
        AWS_SECRET_ACCESS_KEY = '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
'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git url: 'https://github.com/AnilkumarMaguluri18/terraform_file.git', branch: 'main'
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    sh 'terraform apply -auto-approve'
                }
            }
        }
    }
}

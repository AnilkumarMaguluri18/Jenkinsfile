pipeline {
    agent any

    environment {
        AWS_CREDENTIALS_ID = 'my-aws-credentials'
        REPO_URL = 'https://github.com/AnilkumarMaguluri18/terraform_file.git'
        VPC_PATH = 'path/to/vpc/code'
        EC2_PATH = 'path/to/ec2/code'
    }

    stages {
        stage('Checkout VPC') {
            steps {
                script {
                    checkout scm
                    git branch: 'main', url: REPO_URL
                }
            }
        }

        stage('Terraform Init VPC') {
            steps {
                script {
                    dir(VPC_PATH) {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Plan VPC') {
            steps {
                script {
                    dir(VPC_PATH) {
                        sh 'terraform plan -out=vpc_plan'
                    }
                }
            }
        }

        stage('Terraform Apply VPC') {
            steps {
                script {
                    dir(VPC_PATH) {
                        sh 'terraform apply -auto-approve vpc_plan'
                    }
                }
            }
        }

        stage('Downtime') {
            steps {
                script {
                    sleep time: 120, unit: 'MILLISECONDS'
                }
            }
        }

        stage('Checkout EC2') {
            steps {
                script {
                    checkout scm
                    git branch: 'main', url: REPO_URL
                }
            }
        }

        stage('Terraform Init EC2') {
            steps {
                script {
                    dir(EC2_PATH) {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Plan EC2') {
            steps {
                script {
                    dir(EC2_PATH) {
                        sh 'terraform plan -out=ec2_plan'
                    }
                }
            }
        }

        stage('Terraform Apply EC2') {
            steps {
                script {
                    dir(EC2_PATH) {
                        sh 'terraform apply -auto-approve ec2_plan'
                    }
                }
            }
        }
    }
}

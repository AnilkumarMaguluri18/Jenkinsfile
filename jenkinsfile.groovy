pipeline {
    agent any

   environment {
        AWS_CREDENTIALS_ID = 'my-aws-credentials'
     
    }

    stages {
        stage('Checkout VPC') {
            steps {
                script {
                    git 'https://github.com/AnilkumarMaguluri18/terraform_file.git'
                }
            }
        }

        stage('Terraform Init VPC') {
            steps {
                script {
                    dir('path/to/vpc/code') {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Plan VPC') {
            steps {
                script {
                    dir('path/to/vpc/code') {
                        sh 'terraform plan -out=vpc_plan'
                    }
                }
            }
        }

        stage('Terraform Apply VPC') {
            steps {
                script {
                    dir('path/to/vpc/code') {
                        sh 'terraform apply -auto-approve vpc_plan'
                    }
                }
            }
        }

        stage('Downtime') {
            steps {
                script {
                    sleep time: 300, unit: 'MILLISECONDS'
                }
            }
        }

        stage('Checkout EC2') {
            steps {
                script {
                    git 'https://github.com/AnilkumarMaguluri18/ec2.git'
                }
            }
        }

        stage('Terraform Init EC2') {
            steps {
                script {
                    dir('path/to/ec2/code') {
                        sh 'terraform init'
                    }
                }
            }
        }

        stage('Terraform Plan EC2') {
            steps {
                script {
                    dir('path/to/ec2/code') {
                        sh 'terraform plan -out=ec2_plan'
                    }
                }
            }
        }

        stage('Terraform Apply EC2') {
            steps {
                script {
                    dir('path/to/ec2/code') {
                        sh 'terraform apply -auto-approve ec2_plan'
                    }
                }
            }
        }
    }
}

pipeline {
    agent any

    environment {
        JENKINS_HOME = tool 'Terraform'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/AnilkumarMaguluri18/Jenkinsfile.git']]])
            }
        }

        stage('Checkout Terraform') {
            steps {
                dir('terraform') {
                    checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/AnilkumarMaguluri18/terraform_file.git']]])
                }
            }
        }

        stage('Plan') {
            steps {
                script {
                    dir('terraform') {
                        withCredentials([usernamePassword(credentialsId: 'my-aws-credentials', passwordVariable: 'TF_API_TOKEN', usernameVariable: 'TF_USERNAME')]) {
                            script {
                                // Set Terraform environment variables
                                env.TF_PLUGIN_CACHE_DIR = "${JENKINS_HOME}\\terraform\\plugins"
                                env.TF_DATA_DIR = "${JENKINS_HOME}\\terraform\\data"
                                
                                // Initialize Terraform
                                sh 'terraform init -input=false'
                                
                                // Run Terraform plan
                                sh 'terraform plan -out=tfplan -input=false'
                                sh 'terraform show -no-color tfplan > tfplan.txt'
                            }
                        }
                    }
                }
            }
        }

        stage('Approval') {
            steps {
                // Your approval steps here
            }
        }

        stage('Apply') {
            steps {
                script {
                    dir('terraform') {
                        // Run Terraform apply
                        sh 'terraform apply -auto-approve tfplan'
                    }
                }
            }
        }
    }
}

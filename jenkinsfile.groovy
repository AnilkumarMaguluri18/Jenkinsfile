pipeline {
    parameters {
        booleanParam(name: 'autoApprove', defaultValue: false, description: 'Automatically run apply generating plan?')
    }

    agent any

    stages {
        stage('checkout') {
            steps {
                script {
                    dir('terraform') {
                        git "https://github.com/AnilkumarMaguluri18/terraform_file.git"
                    }
                }
            }
        }

        stage('plan') {
            steps {
                script {
                    dir('terraform') {
                        sh 'pwd; terraform init'
                        sh 'pwd; terraform plan -out tfplan'
                        sh 'pwd; terraform show -no-color tfplan > tfplan.txt'
                    }
                }
            }
        }

        stage('Approval') {
            when {
                not {
                    expression { params.autoApprove }
                }
            }
            steps {
                script {
                    def plan = readFile 'terraform/tfplan.txt'
                    input message: 'Do you want to apply the plan?',
                        parameters: [text(name: 'plan', description: 'Please review the plan', defaultValue: plan)]
                }
            }
        }

        stage('Apply') {
            steps {
                script {
                    dir('terraform') {
                        sh 'pwd; terraform apply -input=false tfplan'
                    }
                }
            }
        }
    }
}

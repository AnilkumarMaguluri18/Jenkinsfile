pipeline {
    agent any

    environment {
        TF_HOME = tool 'Terraform'
        GIT_HOME = tool 'Git'
        TF_EXECUTABLE = "${TF_HOME}\\terraform"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                script {
                    // Use the specified Git installation
                    env.PATH = "${GIT_HOME}\\bin;${env.PATH}"
                    
                    // Your SCM checkout steps
                    checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/AnilkumarMaguluri18/Jenkinsfile.git']]])
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    // Use the specified Terraform executable
                    bat "\"${TF_EXECUTABLE}\" init -input=false"
                }
            }
        }

        // Add other stages as needed

    }

    post {
        always {
            // Cleanup steps, if needed
            echo 'Always run cleanup steps here'
        }
    }
}

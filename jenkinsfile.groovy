Pipeline {

parameters {
booleanparam(name: 'autoApprove', defaultValue: false, description:'Automatically run apply generating plan?')
}

agent any
stages {
stage('checkout') {
steps {
script {
dir('terraform')
{
git "https://github.com/AnilkumarMaguluri18/terraform_file.git"
}
}
}
}
Stage('plan') {
steps {
sh'pwd;cd terraform/ ; terraform init'
sh'pwd;cd terraform/ ; terraform plan -out tfplan'
sh'pwd;cd terraform/ ; terraform show -no-colour tfplan > tfplan.txt'
}
}
stage('Aproval') {
when {
not {
equals expected: true, actual: params.autoApprove
}
}
steps {
script {
def paln = readfile 'terraform/tfplan.txt'
input message: "Do you want to apply the plan?",
parameters: [text(name: 'plan', description: 'please review the plan', defaultValue: plan)]
}
}
}
stage('Apply') {
step {
sh "pwd;cd terraform/ ; terraform apply -input=false tfplan"
}
}
}
}

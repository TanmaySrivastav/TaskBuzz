node {
    stage('checking mvn version') {
        bat 'mvn -version'
    }
    stage('clean') {
        bat 'mvn clean'
    }
    stage('deploy') {
        bat 'mvn clean install'
    }

    stage('test') {
        bat 'mvn test'
    }
}

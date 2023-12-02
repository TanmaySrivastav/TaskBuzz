node {
    stage('checking mvn version') {
        bat 'mvn -version'
    }

    stage('deploy') {
        bat 'mvn clean install'
    }

    stage('test') {
        bat 'mvn test'
    }
}

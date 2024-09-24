pipeline {
    agent any
    environment {
        DOCKER_IMAGE="docker_server"
        DOCKER_REGISTRY="registry.hub.docker.com"
        DOCKER_CREDENTIALS=credentials('docker-hub-credentials')
    }
    tools {
        gradle 'gradle_8.10.2'
    }
    stages {
        stage('Checkout') {
            steps {
                // Git 저장소에서 코드를 체크아웃
                git url: 'https://github.com/istanadodan/BootTodoExample.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                // A 폴더로 이동하여 빌드 실행
                dir('BootTodoExample') {
                    // gradlew에 실행 권한 부여
                    sh 'chmod +x ./gradlew'
                    // 빌드 실행
                    sh './gradlew build'
                }
            }
        }
        stage('Test') {
            steps {
                // A 폴더에서 테스트 실행
                dir('BootTodoExample') {
                    sh './gradlew test'
                }
            }
        }
        stage('Deploy') {
            steps {
                dir('BootTodoExample') {
                    docker.build(DOCKER_IMAGE, "-f ./docker/Dockerfile_app .")
                    docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS) {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }
    }
    post {
        always {
            // 항상 실행되는 후처리 작업 (로그 수집 등)
            echo 'Cleaning up...'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}

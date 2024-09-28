pipeline {
    // agent {
    //    docker {
    //              image "docker0now/docker_server"
    //              registryUrl 'https://registry-1.docker.io/'
    //              registryCredentialsId "docker-credential"
    //              reuseNode true
    //            }
    // }
    agent any
    environment {
        DOCKER_IMAGE = 'docker0now/docker_server'
        DOCKER_REGISTRY = 'https://registry-1.docker.io/'
        DOCKER_CREDENTIALS = 'docker-credential'
    }
    tools {
        gradle 'gradle-tools'
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
                    sh './gradlew build --warning-mode=all'
                }
            }
        }
        // stage('Test') {
        //     steps {
        //         // A 폴더에서 테스트 실행
        //         dir('BootTodoExample') {
        //             // s?h './gradlew test'
        //         }
        //     }
        // }
        stage('Deploy') {
            steps {
                dir('BootTodoExample') {
                    script {
                        // try {
                        // Docker Hub에 이미지 푸시
                        docker.withServer('unix:///var/run/docker.sock') {
                            // docker.withRegistry(DOCKER_REGISTRY, credentials(DOCKER_CREDENTIALS)) {
                            docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS) {
                                // 빌드
                                def app = docker.build(DOCKER_IMAGE, '-f ./docker/Dockerfile_app2 ./docker/')
                                // docker hub에 등록
                                app.push()
                            }
                        }

                            // // Docker 로그인
                            // sh "echo $DOCKER_CREDENTIALS_PSW | docker login -u $DOCKER_CREDENTIALS_USR --password-stdin"
                            // sh "docker info"

                            // // Docker 이미지 빌드
                            // sh "docker build -t ${DOCKER_IMAGE} -f ./docker/Dockerfile_app ."

                            // // Docker 이미지 푸시
                            // sh "docker push ${DOCKER_IMAGE}"

                    // // Docker 로그아웃
                    // sh "docker logout"
                    // } catch (Exception e) {
                    //     error "Docker build or push failed: ${e.message}"
                    // }
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

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
        // DOCKER_BOOT_IMAGE = 'docker0now/boot'
        // DOCKER_REAC_IMAGE = 'docker0now/react'
        // DOCKER_REGISTRY = 'https://registry-1.docker.io/'
        DOCKER_BOOT_IMAGE = 'local-registry:5000/boot'
        DOCKER_REAC_IMAGE = 'local-registry:5000/react'
        DOCKER_REGISTRY = 'http://local-registry:5000/'
        DOCKER_CREDENTIALS = 'local-docker-registry'
    }
    tools {
        gradle 'gradle-tools'
        nodejs 'nodejs-tools'
        git 'git-tools'
    }
    stages {
        stage('Checkout') {
            steps {
                    // Git 저장소에서 코드를 체크아웃
                    git branch: 'main',
                        url: 'https://github.com/istanadodan/BootTodoExample.git', 
                        credentialsId: 'git-credential'
                }                
            }
        }
        stage('Build frontend') {
            // when {
            //     changeset "frontend/**"
            // }
            steps {
                // A 폴더로 이동하여 빌드 실행
                dir('Frontend') {
                    nodejs(nodeJSInstallationName: 'nodejs-tools') {                                                
                        sh 'npm install -g typescript'
                        sh 'npm run build'
                        // 빌드 실행
                        // sh 'npm run build'
                    }
                }
            }
        }
        stage('Build backend') {
            // when {
            //     changeset "backend/**"
            // }
            steps {
                // A 폴더로 이동하여 빌드 실행
                dir('Backend') {
                    gradle {
                        tasks 'build'
                        switches '--warning-mode=all'
                        buildFile 'build.gradle'
                    }
                    // gradlew에 실행 권한 부여
                    // sh 'chmod +x ./gradlew'
                    // 빌드 실행
                    // sh './gradlew build --warning-mode=all'
                }
            }
        }
        // stage('Test') {
        //     steps {
        //         // A 폴더에서 테스트 실행
        //         dir('Backend') {
        //             // sh './gradlew test'
        //         }
        //     }
        // }
        stage('Deploy frontend') {
            // when {
            //     changeset "frontend/**"
            // }
            steps {                
                    script {
                        docker.withServer('unix:///var/run/docker.sock') {
                            // docker.withRegistry(DOCKER_REGISTRY, credentials(DOCKER_CREDENTIALS)) {
                            docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS) {
                                // 빌드
                                def app = docker.build(DOCKER_REAC_IMAGE, '-f ./docker/Dockerfile-fe .')
                                // 배포
                                // app.inside {
                                //     sh 'cp -r ./Frontend/build/* /usr/share/nginx/html/'
                                // }
                                //  // 컨테이너 내부에서 파일 복사 작업 수행
                                // app.withRun { container ->
                                //     // Frontend 빌드 결과물을 Nginx 폴더로 복사
                                //     sh "docker exec ${container.id} cp -r ./Frontend/build/* /usr/share/nginx/html/"
                                // }
                                // docker hub에 등록
                                app.push()
                            }
                        }
                }
            }
        }

        stage('Deploy backend') {
            // when {
            //     changeset "backend/**"
            // }
            steps {                
                // dir('Backend') {
                    script {
                        // try {
                        // Docker Hub에 이미지 푸시
                        docker.withServer('unix:///var/run/docker.sock') {
                            // docker.withRegistry(DOCKER_REGISTRY, credentials(DOCKER_CREDENTIALS)) {
                            docker.withRegistry(DOCKER_REGISTRY, DOCKER_CREDENTIALS) {
                                // 빌드
                                def app = docker.build(DOCKER_BOOT_IMAGE, '-f ./docker/Dockerfile-be .')
                                // docker hub에 등록
                                app.push()
                            }
                        }
                    // } catch (Exception e) {
                    //     error "Docker build or push failed: ${e.message}"
                    // }
                    }
                // }
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

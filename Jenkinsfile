pipeline {
    agent any

    tools {
        nodejs "nodejs"
    }

    stages {
        stage('Project Build') {
            steps {
                script {
                    if(env.BRANCH_NAME == 'dev-front') {
                        echo "Front Project Build Step"
                    } else if(env.BRANCH_NAME == 'feature-back/auth-server') {
                        echo "Auth Server Project Build Step"
                        dir('Backend/AuthServer') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    } else if(env.BRANCH_NAME == 'feature-back/member') {
                        echo "ConstelinkMember Project Build Step"
                        dir('Backend/ConstelinkMember') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    } else if(env.BRANCH_NAME == 'feature-back/beneficiary') {
                        echo "ConstelinkBeneficiary Project Build Step"
                        dir('Backend/ConstelinkBeneficiary') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    } else if(env.BRANCH_NAME == 'feature-back/fundraising') {
                        echo "ConstelinkFundraising Project Build Step"
                        dir('Backend/ConstelinkFundraising') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    } else if(env.BRANCH_NAME == 'feature-back/file') {
                        echo "ConstelinkFile Project Build Step"
                        dir('Backend/ConstelinkFile') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    } else if(env.BRANCH_NAME == 'feature-back/notice') {
                        echo "ConstelinkNotice Project Build Step"
                        dir('Backend/ConstelinkNotice') {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    }
                }
            }
        }
        stage('Image Build') {
            environment {
                PATH = "/busybox:/kaniko:$PATH"
            }
            steps {
                script {
                    podTemplate(yaml: """
                      kind: Pod
                      metadata:
                        name: kaniko
                      spec:
                        containers:
                        - name: kaniko
                          image: gcr.io/kaniko-project/executor:debug
                          imagePullPolicy: Always
                          command:
                          - sleep
                          args:
                          - 99d
                          volumeMounts:
                          - name: shared-workspace
                            mountPath: /workspace
                          - name: docker-config
                            mountPath: /kaniko/.docker
                          tty: true
                        nodeSelector:
                          node-role.kubernetes.io/control-plane: ""
                        volumes:
                        - name: shared-workspace
                          hostPath:
                            path: ${WORKSPACE}
                            type: Directory
                        - name: docker-config
                          secret:
                            secretName: regcred
                            items:
                            - key: .dockerconfigjson
                              path: config.json
                        """) {
                        node(POD_LABEL) {
                            container(name: 'kaniko', shell: '/busybox/sh') {
                                if(env.BRANCH_NAME == 'dev-front') {
                                    echo "Front Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Frontend --dockerfile=/workspace/Frontend/Dockerfile --destination=sadoruin/constelink-front:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/auth-server') {
                                    echo "Auth Server Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/AuthServer --dockerfile=/workspace/Backend/AuthServer/Dockerfile --destination=sadoruin/constelink-authserver:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/member') {
                                    echo "ConstelinkMember Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/ConstelinkMember --dockerfile=/workspace/Backend/ConstelinkMember/Dockerfile --destination=sadoruin/constelink-member:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/beneficiary') {
                                    echo "ConstelinkBeneficiary Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/ConstelinkBeneficiary --dockerfile=/workspace/Backend/ConstelinkBeneficiary/Dockerfile --destination=sadoruin/constelink-beneficiary:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/fundraising') {
                                    echo "ConstelinkFundraising Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/ConstelinkFundraising --dockerfile=/workspace/Backend/ConstelinkFundraising/Dockerfile --destination=sadoruin/constelink-fundraising:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/file') {
                                    echo "ConstelinkFile Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/ConstelinkFile --dockerfile=/workspace/Backend/ConstelinkFile/Dockerfile --destination=sadoruin/constelink-file:${env.BUILD_NUMBER}
                                    """
                                } else if(env.BRANCH_NAME == 'feature-back/notice') {
                                    echo "ConstelinkNotice Image Build Step"
                                    sh """#!/busybox/sh
                                    /kaniko/executor --context=/workspace/Backend/ConstelinkNotice --dockerfile=/workspace/Backend/ConstelinkNotice/Dockerfile --destination=sadoruin/constelink-notice:${env.BUILD_NUMBER}
                                    """
                                }
                            }
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    dir('/git') {
                        git branch: 'main',
                            credentialsId: 'gitlab-account',
                            url: 'https://lab.ssafy.com/dope2514/s08p22a206-manifest'
                        sh 'git config --global user.email "dope2514@gmail.com"'
                        sh 'git config --global user.name "SadoRuin"'

                        if(env.BRANCH_NAME == 'dev-front') {
                            echo "Front Deploy Step"
                            sh """
                                sed -i 's/constelink-front:\\([^:]*\\)/constelink-front:${env.BUILD_NUMBER}/g' manifests/constelink-front.yaml
                                git add manifests/constelink-front.yaml
                                git commit -m 'Update constelink-front tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/auth-server') {
                            echo "Auth Server Deploy Step"
                            sh """
                                sed -i 's/constelink-authserver:\\([^:]*\\)/constelink-authserver:${env.BUILD_NUMBER}/g' manifests/constelink-authserver.yaml
                                git add manifests/constelink-authserver.yaml
                                git commit -m 'Update constelink-authserver tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/member') {
                            echo "ConstelinkMember Deploy Step"
                            sh """
                                sed -i 's/constelink-member:\\([^:]*\\)/constelink-member:${env.BUILD_NUMBER}/g' manifests/constelink-member.yaml
                                git add manifests/constelink-member.yaml
                                git commit -m 'Update constelink-member tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/beneficiary') {
                            echo "ConstelinkBeneficiary Deploy Step"
                            sh """
                                sed -i 's/constelink-beneficiary:\\([^:]*\\)/constelink-beneficiary:${env.BUILD_NUMBER}/g' manifests/constelink-beneficiary.yaml
                                git add manifests/constelink-beneficiary.yaml
                                git commit -m 'Update constelink-beneficiary tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/fundraising') {
                            echo "ConstelinkFundraising Deploy Step"
                            sh """
                                sed -i 's/constelink-fundraising:\\([^:]*\\)/constelink-fundraising:${env.BUILD_NUMBER}/g' manifests/constelink-fundraising.yaml
                                git add manifests/constelink-fundraising.yaml
                                git commit -m 'Update constelink-fundraising tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/file') {
                            echo "ConstelinkFile Deploy Step"
                            sh """
                                sed -i 's/constelink-file:\\([^:]*\\)/constelink-file:${env.BUILD_NUMBER}/g' manifests/constelink-file.yaml
                                git add manifests/constelink-file.yaml
                                git commit -m 'Update constelink-file tag to ${env.BUILD_NUMBER}'
                            """
                        } else if(env.BRANCH_NAME == 'feature-back/notice') {
                            echo "ConstelinkNotice Deploy Step"
                            sh """
                                sed -i 's/constelink-notice:\\([^:]*\\)/constelink-notice:${env.BUILD_NUMBER}/g' manifests/constelink-notice.yaml
                                git add manifests/constelink-notice.yaml
                                git commit -m 'Update constelink-notice tag to ${env.BUILD_NUMBER}'
                            """
                        }

                        withCredentials([usernamePassword(credentialsId: 'gitlab-account', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                            sh 'git remote set-url origin https://$GIT_USERNAME:$GIT_PASSWORD@lab.ssafy.com/dope2514/s08p22a206-manifest.git'
                            sh 'git push origin main'
                        }
                    }

                }
            }
        }
    }
}

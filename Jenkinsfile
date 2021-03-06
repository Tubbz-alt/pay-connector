#!/usr/bin/env groovy

pipeline {
  agent any

  parameters {
    booleanParam(defaultValue: false, description: '', name: 'runEndToEndTestsOnPR')
    string(defaultValue: 'master', description: 'Branch of pay-scripts to use when running e2e', name: 'payScriptsBranch')
  }

  options {
    timestamps()
  }

  libraries {
    lib("pay-jenkins-library@master")
  }

  environment {
    DOCKER_HOST = "unix:///var/run/docker.sock"
    RUN_END_TO_END_ON_PR = "${params.runEndToEndTestsOnPR}"
    JAVA_HOME = "/usr/lib/jvm/java-1.11.0-openjdk-amd64"
    PAY_SCRIPTS_BRANCH = "${params.payScriptsBranch}"
  }
  stages {
    stage('Maven Build') {
      steps {
        script {
          def stepBuildTime = System.currentTimeMillis()
          def commit = gitCommit()
          def branchName = gitBranchName()

          withCredentials([
              string(credentialsId: 'pact_broker_username', variable: 'PACT_BROKER_USERNAME'),
              string(credentialsId: 'pact_broker_password', variable: 'PACT_BROKER_PASSWORD')]
          ) {
            sh 'mvn -version'
            sh "mvn clean verify pact:publish -DPACT_BROKER_URL=https://pact-broker-test.cloudapps.digital -DPACT_CONSUMER_VERSION=${commit}" +
                " -DPACT_BROKER_USERNAME=${PACT_BROKER_USERNAME} -DPACT_BROKER_PASSWORD=${PACT_BROKER_PASSWORD} -DPACT_CONSUMER_TAG=${branchName}"
          }
          postSuccessfulMetrics("connector.maven-build", stepBuildTime)
        }
      }
      post {
        failure {
          postMetric("connector.maven-build.failure", 1)
        }
      }
    }
    stage('Docker Build') {
      steps {
        script {
          buildAppWithMetrics {
            app = "connector"
          }
        }
      }
      post {
        failure {
          postMetric("connector.docker-build.failure", 1)
        }
      }
    }
    stage('Contract Tests: Connector as Provider') {
      steps {
        script {
          def stepBuildTime = System.currentTimeMillis()
          runProviderContractTests()
          postSuccessfulMetrics("connector.provider-contract-tests", stepBuildTime)
        }
      }
      post {
        failure {
          postMetric("connector.provider-contract-tests.failure", 1)
        }
      }
    }
    stage('Contract Tests: Providers to Connector') {
      steps {
        script {
          env.PACT_TAG = gitBranchName()
        }
        ws('contract-tests-wp') {
          runPactProviderTests("pay-ledger", "${env.PACT_TAG}", "connector")
        }
      }
      post {
        always {
          ws('contract-tests-wp') {
            deleteDir()
          }
        }
      }
    }
    stage('Tests') {
      failFast true
      stages {
        stage('End-to-End Tests') {
          when {
            anyOf {
              branch 'master'
              environment name: 'RUN_END_TO_END_ON_PR', value: 'true'
            }
          }
          steps {
            runAppE2E("connector", "card")
          }
        }
      }
    }
    stage('Docker Tag') {
      steps {
        script {
          dockerTagWithMetrics {
            app = "connector"
          }
        }
      }
      post {
        failure {
          postMetric("connector.docker-tag.failure", 1)
        }
      }
    }
    stage('Deploy') {
      when {
        branch 'master'
      }
      steps {
        checkPactCompatibility("connector", gitCommit(), "test")
        deployEcs("connector")
      }
    }
    stage('Smoke Tests') {
      when { branch 'master' }
      steps { runCardSmokeTest() }
    }
    stage('Pact Tag') {
      when {
        branch 'master'
      }
      steps {
        echo 'Tagging provider pact with "test"'
        tagPact("connector", gitCommit(), "test")
      }
    }
    stage('Complete') {
      failFast true
      parallel {
        stage('Tag Build') {
          when {
            branch 'master'
          }
          steps {
            tagDeployment("connector")
          }
        }
        stage('Trigger Deploy Notification') {
          when {
            branch 'master'
          }
          steps {
            triggerGraphiteDeployEvent("connector")
          }
        }
      }
    }
  }
  post {
    failure {
      postMetric(appendBranchSuffix("connector") + ".failure", 1)
    }
    success {
      postSuccessfulMetrics(appendBranchSuffix("connector"))
    }
    always {
      junit "**/target/surefire-reports/*.xml,**/target/failsafe-reports/*.xml"
    }
  }
}
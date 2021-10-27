package br.com.fcortes

import groovy.json.JsonOutput

/**
 * Base Slack Notification class
 */
class SlackNotification {
    protected String messageType

    // Slack variables
    protected String slackUser
    protected String slackChannel
    protected String slackURL

    // Jenkins variables
    protected Integer jenkinsBuildNumber
    protected String jenkinsBuildURL
    protected String jenkinsJobName
    protected String jenkinsBuildTime
    protected String jenkinsTestStatus = 'message'

    SlackNotification(String messageType, String slackUser, String slackChannel, String slackURL, Integer jenkinsBuildNumber, String jenkinsBuildURL, String jenkinsJobName, String jenkinsBuildTime) {
        this.messageType = messageType
        this.slackUser = slackUser
        this.slackChannel = slackChannel
        this.slackURL = slackURL
        this.jenkinsBuildNumber = jenkinsBuildNumber
        this.jenkinsBuildURL = jenkinsBuildURL
        this.jenkinsJobName = jenkinsJobName
        this.jenkinsBuildTime = jenkinsBuildTime
    }

    private String success() {
        return JsonOutput.toJson([
            channel: slackChannel,
            username: slackUser,
            attachments: [
                [
                    color: '#36a64f',
                    title: 'Success: ${jenkinsJobName} - #${jenkinsBuildNumber} Execution finished after ${jenkinsBuildTime}',
                    text: jenkinsTestStatus,
                    fallback: "Access your build here: ${jenkinsBuildURL}",
                    actions: [
                        [
                            type: 'button',
                            text: 'Open in Jenkins',
                            url: "${jenkinsBuildURL}"
                        ]
                    ]
                ]
            ]
        ])
    }

    private String failure() {
        return JsonOutput.toJson([
            channel: slackChannel,
            username: slackUser,
            attachments: [
                [
                    color: '#ff0000',
                    title: 'Failure: ${jobName} - #${buildNumber} Execution finished after ${buildTime}',
                    text: jenkinsTestStatus,
                    fallback: "Access your build here: ${jenkinsBuildURL}",
                    actions: [
                            [
                                    type: 'button',
                                    text: 'Open in Jenkins',
                                    url: "${jenkinsBuildURL}"
                            ]
                    ]
                ]
            ]
        ])
    }

    void sendMessage(def steps) {
        def payload = "$messageType"()

        steps.sh "curl -X POST --data-urlencode \'payload=${payload}\' ${slackURL}"
    }
}

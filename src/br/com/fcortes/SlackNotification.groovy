package br.com.fcortes

import groovy.json.JsonOutput

/**
 * Base Slack Notification class
 */
class SlackNotification {
    private String messageType

    // Slack variables
    private String slackUser
    private String slackChannel
    private String slackURL

    // Jenkins variables
    private Integer jenkinsBuildNumber
    private String jenkinsBuildURL
    private String jenkinsJobName
    private String jenkinsBuildTime

    public SlackNotification(String messageType, String slackUser, String slackChannel, String slackURL, Integer jenkinsBuildNumber, String jenkinsBuildURL, String jenkinsJobName, String jenkinsBuildTime) {
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
                    icon_emoji: ':ghost:',
                    title: "Success Deploy: ${jenkinsJobName}",
                    text: "#${jenkinsBuildNumber} Execution finished after ${jenkinsBuildTime}",
                    fallback: "Access your build here: ${jenkinsBuildURL}",
                    actions: [
                        [
                            type: 'button',
                            text: 'Open in Jenkins',
                            url: jenkinsBuildURL
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
                    icon_emoji: ':ghost:',
                    title: "Failure Deploy: ${jenkinsJobName}",
                    text: "#${jenkinsBuildNumber} Execution finished after ${jenkinsBuildTime}",
                    fallback: "Access your build here: ${jenkinsBuildURL}",
                    actions: [
                        [
                            type: 'button',
                            text: 'Open in Jenkins',
                            url: jenkinsBuildURL
                        ]
                    ]
                ]
            ]
        ])
    }

    public void sendMessage(def steps) {
        def payload = "$messageType"()

        steps.sh "curl -X POST --data-urlencode \'payload=${payload}\' ${slackURL}"
    }
}

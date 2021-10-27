import br.com.fcortes.SlackNotification

def call() {
    SlackNotification notification = new SlackNotification('success', env.SLACK_USER, env.SLACK_CHANNEL, env.SLACK_URL, currentBuild.number, currentBuild.absoluteUrl, currentBuild.projectName, currentBuild.durationString)

    notification.sendMessage(this)
}

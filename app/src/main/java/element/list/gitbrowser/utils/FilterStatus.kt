package element.list.gitbrowser.utils

enum class FilterStatus(var filter: String) {
    STARS("stargazers_count"),
    FORKS("forks_count"),
    UPDATED("updated_at")
}
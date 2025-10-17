package io.github.tungnk123.notionsdkkmp.core.data.model

enum class NotionApiVersion(val stringValue: String) {
    V2021_08_16("2021-08-16"),
    V2022_02_22("2022-02-22"),
    V2022_06_28("2022-06-28"),
    V2025_09_03("2025-09-03");

    companion object {
        val LATEST: NotionApiVersion = V2025_09_03
    }
}

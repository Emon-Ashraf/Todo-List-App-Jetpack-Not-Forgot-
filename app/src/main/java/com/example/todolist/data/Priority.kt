package com.example.todolist.data

enum class Priority(val displayName: String) {
    IMPORTANT("important"),
    NORMAL("normal"),
    NOT_IMPORTANT("not important"),
    SOME_DAY("some day");

    companion object {
        fun fromDisplayName(displayName: String): Priority? {
            return values().find { it.displayName == displayName }
        }
    }
}

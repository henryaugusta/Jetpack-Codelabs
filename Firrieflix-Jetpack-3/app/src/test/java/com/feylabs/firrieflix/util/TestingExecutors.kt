package com.feylabs.firrieflix.util

import java.util.concurrent.Executor

object TestingExecutors : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}
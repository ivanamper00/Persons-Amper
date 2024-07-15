package com.amper.personapp.util

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    val io: Scheduler
    val main: Scheduler
}
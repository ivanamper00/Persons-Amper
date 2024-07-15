package com.amper.personapp.util

import io.reactivex.rxjava3.core.Scheduler

class AppSchedulerProvider(override val io: Scheduler, override val main: Scheduler) : SchedulerProvider
package ru.ogbozoyan.cron.service.pg

import org.springframework.context.ApplicationEvent
import org.springframework.core.io.Resource

data class PgEvent(val resource: Resource, val type: FileTypeEnum, val fileName: String?) : ApplicationEvent(resource)
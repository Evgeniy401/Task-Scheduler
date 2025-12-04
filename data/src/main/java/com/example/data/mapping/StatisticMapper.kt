package com.example.data.mapping

import com.example.data.storage.entity.StatisticEntity
import com.example.domain.model.StatisticDomainModel

object StatisticMapper {

    fun toDomain(entity: StatisticEntity): StatisticDomainModel {
        return StatisticDomainModel(
            totalCreated = entity.totalCreated,
            totalCompleted = entity.totalCompleted,
            totalDeleted = entity.totalDeleted
        )
    }

    fun toEntity(domain: StatisticDomainModel): StatisticEntity {
        return StatisticEntity(
            id = 1,
            totalCreated = domain.totalCreated,
            totalCompleted = domain.totalCompleted,
            totalDeleted = domain.totalDeleted
        )
    }
}
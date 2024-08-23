package com.carbon.data.util

interface Mapper<Entity, Domain> {

    fun to(domain: Domain): Entity

    fun from(entity: Entity): Domain

    fun mapModelList(models: List<Entity>): List<Domain> {
        return models.mapTo(mutableListOf(), ::from)
    }

    fun mapEntityList(entityList: List<Domain>): List<Entity> {
        return entityList.mapTo(mutableListOf(), this::to)
    }
}

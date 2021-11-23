package com.lohanna.nekopics.di

import com.lohanna.nekopics.db.entity.CatEntity
import com.lohanna.nekopics.model.CatModel

object Transformer {

    fun convertCatModelToCatEntity(cat: CatModel): CatEntity {
        return CatEntity(
            id = cat.id,
            url = cat.url
        )
    }

    fun convertCatEntityToCatModel(catEntity: CatEntity): CatModel {
        return CatModel(
            id = catEntity.id,
            url = catEntity.url
        )
    }

}
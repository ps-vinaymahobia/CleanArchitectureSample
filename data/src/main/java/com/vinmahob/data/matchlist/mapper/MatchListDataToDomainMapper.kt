package com.vinmahob.data.matchlist.mapper

import com.vinmahob.data.matchlist.model.MatchListDataModel
import com.vinmahob.domain.matchlist.model.MatchListDomainModel

class MatchListDataToDomainMapper(
    private val matchListItemDataToDomainMapper: MatchListItemDataToDomainMapper
) {
    fun toDomain(input: MatchListDataModel): MatchListDomainModel = with(input) {
        return MatchListDomainModel(
            matchList = data.map { item ->
                matchListItemDataToDomainMapper.toDomain(
                    item
                )
            }
        )
    }
}
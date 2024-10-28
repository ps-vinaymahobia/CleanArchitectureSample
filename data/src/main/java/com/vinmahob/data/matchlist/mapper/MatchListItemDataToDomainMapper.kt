package com.vinmahob.data.matchlist.mapper

import com.vinmahob.data.matchlist.model.MatchListItemDataModel
import com.vinmahob.domain.matchlist.model.MatchListItemDomainModel

class MatchListItemDataToDomainMapper {
    fun toDomain(input: MatchListItemDataModel) = with(input) {
        MatchListItemDomainModel(
            id = id,
            name = name,
            matchType = matchType,
        )
    }
}
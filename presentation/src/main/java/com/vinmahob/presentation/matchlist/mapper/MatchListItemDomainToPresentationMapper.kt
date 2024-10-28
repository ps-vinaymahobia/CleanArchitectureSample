package com.vinmahob.presentation.matchlist.mapper

import com.vinmahob.domain.matchlist.model.MatchListItemDomainModel
import com.vinmahob.presentation.matchlist.model.MatchListItemPresentationModel

class MatchListItemDomainToPresentationMapper {
    fun toPresentation(input: MatchListItemDomainModel) = with(input) {
        MatchListItemPresentationModel(
            id = id,
            name = name,
            matchType = matchType,
        )
    }
}
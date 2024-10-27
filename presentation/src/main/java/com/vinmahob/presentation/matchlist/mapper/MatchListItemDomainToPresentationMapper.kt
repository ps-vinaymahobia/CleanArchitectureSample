package com.vinmahob.presentation.matchlist.mapper

import com.vinmahob.domain.matchlist.model.MatchListItemDomainModel
import com.vinmahob.presentation.matchlist.model.MatchListItemPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListItemPresentationModel

class MatchListItemDomainToPresentationMapper {
    fun toPresentation(input: MatchListItemDomainModel) = with(input) {
        MatchListItemPresentationModel(
            id = id,
            name = name,
            matchType = matchType,
        )
    }
}
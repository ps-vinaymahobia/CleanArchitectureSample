package com.vinmahob.presentation.matchlist.mapper

import com.vinmahob.domain.matchlist.model.MatchListDomainModel
import com.vinmahob.presentation.matchlist.model.MatchListPresentationModel

class MatchListDomainToPresentationMapper(
    private val matchListItemDomainToPresentationMapper: MatchListItemDomainToPresentationMapper
) {
    fun toPresentation(input: MatchListDomainModel): MatchListPresentationModel = with(input) {
        return MatchListPresentationModel(
            matchList = matchList.map { item ->
                matchListItemDomainToPresentationMapper.toPresentation(
                    item
                )
            }
        )
    }
}
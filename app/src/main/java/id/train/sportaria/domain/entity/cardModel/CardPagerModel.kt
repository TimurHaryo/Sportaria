package id.train.sportaria.domain.entity.cardModel

import id.train.sportaria.util.view.CardPagerRules

data class CardPagerModel(
    var data: String = "",
    var title: String = "",
    var rule: CardPagerRules = CardPagerRules.TEXT
)
package it.simo.outcomecompose.domain

import it.simo.outcomecompose.models.Layout

sealed class OutcomeLayoutType {
    object Classic : OutcomeLayoutType()
    object AdditionalInfoPicker : OutcomeLayoutType()
}

fun Layout.getOutcomeLayoutType(): OutcomeLayoutType? {
    if (layoutType == "normal" && additionalInfo == null) {
        return OutcomeLayoutType.Classic
    }

    if (layoutType == "normal" && additionalInfo == "picker") {
        return OutcomeLayoutType.AdditionalInfoPicker
    }

    return null
}
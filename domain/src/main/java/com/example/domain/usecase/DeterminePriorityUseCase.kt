package com.example.domain.usecase

import com.example.domain.model.PriorityDomain
import javax.inject.Inject

class DeterminePriorityUseCase @Inject constructor() {
    operator fun invoke(selectedPriority: PriorityDomain): PriorityDomain {
        return if (selectedPriority == PriorityDomain.NONE) {
            PriorityDomain.STANDARD
        } else {
            selectedPriority
        }
    }
}
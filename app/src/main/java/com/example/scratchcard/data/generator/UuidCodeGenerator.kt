package com.example.scratchcard.data.generator

import com.example.scratchcard.domain.generator.CodeGenerator
import java.util.UUID
import javax.inject.Inject

class UuidCodeGenerator @Inject constructor() : CodeGenerator {
    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}

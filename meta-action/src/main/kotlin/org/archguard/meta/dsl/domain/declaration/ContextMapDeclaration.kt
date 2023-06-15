package org.archguard.meta.dsl.domain.declaration

import org.archguard.meta.dsl.domain.MappingDefine

class ContextMapDeclaration(name: String) {
    fun context(name: String, function: ContextDeclaration.() -> Unit): ContextDeclaration {
        val contextDeclaration = ContextDeclaration(name)
        contextDeclaration.function()
        return contextDeclaration
    }

    fun context(name: String): ContextDeclaration {
        return ContextDeclaration(name)
    }

    fun mapping(function: () -> Unit): MappingDefine {
        val mapping = MappingDefine()
        function()
        return mapping
    }
}
package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.restful.ApiAtomicRule
import org.archguard.meta.model.RestApiElement

class HttpActionRule(private val actions: List<String>) : ApiAtomicRule("http-action", "supported http actions: ${actions.joinToString(", ")}") {
    override fun exec(input: RestApiElement): List<RuleResult> {
        return listOf(RuleResult(this.actionName, this.rule, actions.contains(input.httpAction)))
    }
}
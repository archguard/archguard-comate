package org.archguard.meta.base

interface LlmVerifierRule<T : Any> : Rule<T> {
    override fun exec(input: T): List<RuleResult> {
        return exec(input)
    }
}
package org.archguard.meta


@DslMarker
annotation class AtomicDsl

interface AtomicAction {
}

/**
 * Use LLM to verify the rule
 */
interface LlmRuleVerifier {
    fun check(prompt: String, content: String): Boolean {
        return false
    }
}

class FakeRuleVerifier : LlmRuleVerifier {
    override fun check(prompt: String, content: String): Boolean {
        return true
    }
}


open class Element

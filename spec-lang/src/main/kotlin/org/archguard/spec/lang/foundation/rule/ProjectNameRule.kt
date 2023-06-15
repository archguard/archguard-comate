package org.archguard.spec.lang.foundation.rule

import org.archguard.spec.base.Rule
import org.archguard.spec.lang.base.BaseDeclaration
import org.archguard.spec.lang.base.PatternWithExampleRule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.model.FoundationElement

class ProjectNameRule : PatternWithExampleRule<FoundationElement>, BaseDeclaration<FoundationElement> {
    override val actionName = "ProjectName"
    private var originRegex: String = ""
    private var ruleRegex: Regex? = null

    private var sample = ""

    override fun pattern(regex: String) {
        this.originRegex = regex
        try {
            this.ruleRegex = Regex(regex)
        } catch (e: Exception) {
            throw Exception("Invalid regex: $regex")
        }
    }

    override fun example(sample: String) {
        this.sample = sample
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val ruleExplain = "regex: " + this.originRegex + "sample: " + sample

        if (ruleRegex != null) {
            val matchResult = ruleRegex!!.find(input.projectName)
            return listOf(RuleResult(this.actionName, ruleExplain, matchResult != null))
        }

        return listOf(RuleResult(this.actionName, ruleExplain, false))
    }

    override fun rules(element: FoundationElement): List<Rule<FoundationElement>> {
        return listOf(this)
    }
}
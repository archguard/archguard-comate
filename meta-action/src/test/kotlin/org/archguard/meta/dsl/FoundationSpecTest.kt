package org.archguard.meta.dsl

import chapi.domain.core.CodeDataStruct
import org.archguard.meta.base.FakeRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.matcher.shouldBe
import org.archguard.meta.dsl.matcher.shouldNotBe
import org.archguard.meta.model.FoundationElement
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FoundationSpecTest {
    private val governance = foundation {
        project_name {
            pattern("^([a-z0-9-]+)-([a-z0-9-]+)-([a-z0-9-]+)(-common)?\$")
            example("system1-servicecenter1-microservice1")
        }

        layered {
            layer("application") {
                pattern(".*\\.application") { name shouldBe endWiths("DTO", "Request", "Response") }
            }

            layer("domain") {
                pattern(".*\\.domain") { name shouldBe endWiths("Entity") }
            }

            dependency {
                "domain" dependedOn "infrastructure"
                "application" dependedOn "domain"
            }
        }

        naming {
            class_level {
                style("CamelCase")
                pattern(".*") { name shouldNotBe contains("$") }
            }
            function_level {
                style("CamelCase")
                pattern(".*") { name shouldNotBe contains("$") }
            }
        }
    }

    @Test
    fun should_equal_when_had_correct_project_name() {
        val foundation = FoundationElement("error-project_name", listOf())
        governance.context(FakeRuleVerifier())
        val results: List<RuleResult> = governance.exec(foundation)

        results.filter { it.ruleName == "ProjectName" }.forEach {
            assertEquals(it.result, false)
        }
    }

    @Test
    fun should_return_false_when_class_error() {
        val ds = CodeDataStruct("error_Class_Name")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))
        governance.context(FakeRuleVerifier())

        val result: List<RuleResult> = governance.exec(foundation)

        result.filter { it.ruleName == "ProjectName" }.forEach {
            assertEquals(it.result, true)
        }
        result.filter { it.ruleName == "NamingItem for Class" }.forEach {
            assertEquals(it.result, false)
        }
    }

    @Test
    fun should_return_true_when_class_name_correct() {
        val ds = CodeDataStruct("ClassName")
        val foundation = FoundationElement("system1-servicecenter1-microservice1", listOf(ds))
        governance.context(FakeRuleVerifier())

        val result: List<RuleResult> = governance.exec(foundation)

        result.filter { it.ruleName == "ProjectName" }.forEach {
            assertEquals(it.result, true)
        }

        // todo: make it works better
        result.filter { it.ruleName == "Naming" }.forEach {
            assertEquals(it.result, true)
        }
    }
}
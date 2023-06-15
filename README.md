# ArchGuard Co-mate

[![Java support](https://img.shields.io/badge/Java-11+-green?logo=java&logoColor=white)](https://openjdk.java.net/)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/archguard/co-mate)
[![Coverage Status](https://coveralls.io/repos/github/archguard/co-mate/badge.svg?branch=master)](https://coveralls.io/github/archguard/co-mate?branch=master)

> Co-mate is an AI-powered software architecture copilot, design and governance tools.

Project Kanban: https://github.com/orgs/archguard/projects/5/views/1

Supported languages by [ArchGuard](https://github.com/archguard/archguard) and [Chapi](https://github.com/modernizing/chapi):
Java, Kotlin, TypeScript/JavaScript, Golang, Python

## Design Principles

1. DSL as Ubiquitous Language: Empower efficient communication between humans and machines by employing a Domain-Specific Language (DSL).
2. Atomic LLM for Orchestration: Harness the atomic capabilities of a Language Model (LLM) to construct intricate behaviors within the DSL.
3. Dynamic Context by Layered: Divide the context into layers to handle complexities effectively through the LLM.

## Usage

1. download the latest release from [release page](https://github.com/archguard/co-mate/releases)
2. please put `OPENAI_API_KEY=xxx` in `~/.comate/.env`
3. run `java -jar comate-cli-*-all.jar` replace `*` with the version number

`.env` file example:

```
OPENAI_API_KEY=xxx
OPENAI_API_PROXY=xxxx (optional, if you had a OpenAI proxy server)
```

## Modules

- `comate-cli`: command line interface
- `comate-gui`: graphical user interface

Core modules:

- `comate-core`: core logic, handle ArchGuard API, LLM logic, etc.
- LLM modules:
    - `llm-core`: large language model core, like OpenAI call, Token calculate, Tokenizer, Embedding, Similarity for
      Embedding, etc.
- Architecture Spec
    - `spec-runtime`: the Kotlin REPL/Interpreter for Architecture Spec DSL
    - `spec-partitioner`: the partitioner for partitioning the architecture spec document
- Architecture Define
    - `architecture`: architecture model

## Specification DSL

- Domain (TODO). spec for Domain Driven Design.
- Foundation (WIP). basic spec, like Project naming, Package and Class and Function naming.
- RestApi. spec for RESTful API.
- ....

### Domain

```kotlin
domain {
    context_map("TicketBooking") {
        context("Reservation") {}
        context("Ticket") {}

        mapping {
            context("Reservation") dependedOn context("Ticket")
            context("Reservation") dependedOn context("Movie")
        }
    }
}
```

### Foundation

```kotlin
foundation {
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
        layer("infrastructure") {
            pattern(".*\\.infrastructure") { name shouldBe endWiths("Repository", "Mapper") }
        }
        layer("interface") {
            pattern(".*\\.interface") { name shouldBe endWiths("Controller", "Service") }
        }

        dependency {
            "application" dependedOn "domain"
            "application" dependedOn "interface"
            "domain" dependedOn "infrastructure"
            "interface" dependedOn "domain"
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
```

### REST API

```kotlin
rest_api {
    uri_construction {
        pattern("/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
        example("/api/petstore/v1/pets/dogs")
    }

    http_action("GET", "POST", "PUT", "DELETE")
    status_code(200, 201, 202, 204, 400, 401, 403, 404, 500, 502, 503, 504)

    security(
        """
Token Based Authentication (Recommended) Ideally, microservices should be stateless so the service instances can be scaled out easily and the client requests can be routed to multiple independent service providers. A token based authentication mechanism should be used instead of session based authentication
        """.trimIndent()
    )

    misc("""""")
}
```

License
---

This code is distributed under the MPL license. See `LICENSE` in this directory.

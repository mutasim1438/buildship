package Buildship.Check.Eclipse_Experimental

import Buildship.Check.Eclipse_Experimental.buildTypes.Eclipse_Experimental_Eclipse46withKotlinSupport
import jetbrains.buildServer.configs.kotlin.v2018_1.Project

object Project : Project({
    id("Eclipse_Experimental")
    name = "Experimental"
    description = "Build configurations to produce experimental artifacts"

    buildType(Eclipse_Experimental_Eclipse46withKotlinSupport)
})
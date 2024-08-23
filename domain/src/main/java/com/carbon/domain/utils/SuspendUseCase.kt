package com.carbon.domain.utils

abstract class SuspendUseCase<in Param, out Output> {

    abstract suspend operator fun invoke(param: Param): Output
}

// No need to pass "Unit" as a parameter
suspend operator fun <Output> SuspendUseCase<Unit, Output>.invoke() = this(Unit)

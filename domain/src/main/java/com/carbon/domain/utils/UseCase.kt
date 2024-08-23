package com.carbon.domain.utils

abstract class UseCase<in Param, out Output> {

    abstract operator fun invoke(param: Param): Output
}

// No need to pass "Unit" as a parameter
operator fun <Output> UseCase<Unit, Output>.invoke() = this(Unit)

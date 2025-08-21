package io.github.aungthiha.operation

import java.io.IOException

class DefaultErrorMapper<O> : (Exception) -> Outcome.Failure<O> {

    override fun invoke(exception: Exception): Outcome.Failure<O> =
        Outcome.Failure(FailureType.GENERAL, exception)
}

class DefaultNetworkErrorMapper<O> : (Exception) -> Outcome.Failure<O> {

    override fun invoke(exception: Exception): Outcome.Failure<O> = when (exception) {
        is IOException -> Outcome.Failure(FailureType.NETWORK, exception)
        else -> Outcome.Failure(FailureType.GENERAL, exception)
    }
}

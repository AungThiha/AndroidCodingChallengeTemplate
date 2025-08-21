package io.github.aungthiha.operation

fun <I, O> suspendNetworkOperation(
    mapError: (Exception) -> Outcome.Failure<O> = DefaultNetworkErrorMapper(),
    block: suspend (I) -> O
): SuspendOperation<I, O> = suspendOperation(
    mapError,
    block
)

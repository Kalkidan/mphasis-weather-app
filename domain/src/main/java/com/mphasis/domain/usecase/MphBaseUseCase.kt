package com.mphasis.domain.usecase

import com.mphasis.data.network.helper.MphNetworkResponse
import com.mphasis.data.repository.MphCommonData

/**
 * A common use case for the application
 * at the domain layer
 *
 * @param O the resulting success output from the operation
 *          at the network layer
 * @param E the resulting error output from the operation
 *          at the network layer
 *
 * @author Kal Tadesse
 */

@Suppress("UNCHECKED_CAST")
abstract class MphBaseUseCaseImpl<I, O: MphCommonData, E: MphCommonData> : MphBaseUseCase {

    protected fun MphNetworkResponse.transform() = when (this) {
        is MphNetworkResponse.Success<*> -> processValue(data as I)
        is MphNetworkResponse.Error -> processFailure()
        MphNetworkResponse.Unknown -> processFailure()
    }

    /**
     * Process the failure when inherited by
     * subclasses
     *
     * @return E error to be processed
     */
    protected abstract fun processFailure(): E

    /**
     * Process the success when inherited by
     * subclasses
     *
     * @param value input value to be processed to an output.
     * @return O output of the processed data
     */
    protected abstract fun processValue(value: I): O
}

/**
 * This is just a marker interface for now.
 */
interface MphBaseUseCase
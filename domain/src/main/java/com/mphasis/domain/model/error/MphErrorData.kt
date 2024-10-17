package com.mphasis.domain.model.error

import com.mphasis.domain.model.MphCommonData

/**
 * This will carry error messaging for the app
 *
 * Note - for now we will just be using a generic error.
 */
data class MphErrorData(val message: String): MphCommonData
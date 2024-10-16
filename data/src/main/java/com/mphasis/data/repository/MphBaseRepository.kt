package com.mphasis.data.repository


/**
 * A base repository serving all its subclasses.
 *
 * @param I input to the parsing logic.
 *
 * @author Kal Tadesse
 */
abstract class MphBaseRepositoryImpl<I> : MphBaseRepository

interface MphBaseRepository


/**
 * A marker interface for common use.
 * Also applies for avoiding an implicit cast
 * to Any? at the network layer.
 *
 * @author Kal Tadesse
 */
interface MphCommonData
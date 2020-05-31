package com.example.revolut_test.repository

import com.example.revolut_test.network.NetworkService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test

private const val TEST_BASE = "EUR"

class NetworkRepositoryTest {
    private lateinit var repository: NetworkRepository

    private val service: NetworkService = mock()

    @Before
    fun `set up`() {
        repository = NetworkRepository(service)
    }

    @Test
    fun `getCurrencies - should delegate to network service`() {
        repository.getCurrencies(TEST_BASE)

        verify(service).getCurrencies(TEST_BASE)
        verifyNoMoreInteractions(service)
    }
}
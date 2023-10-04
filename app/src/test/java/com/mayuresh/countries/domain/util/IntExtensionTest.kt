package com.mayuresh.countries.domain.util

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionTest {

    @Test
    fun `test Int to readable`() {
        // Given
        val number = 1234567

        // When
        val readableNumber = number.toReadable()

        // Then
        assertEquals("1 234 567", readableNumber)
    }

    @Test
    fun `test Int to readable with zero`() {
        // Given
        val number = 0

        // When
        val readableNumber = number.toReadable()

        // Then
        assertEquals("0", readableNumber)
    }

    @Test
    fun `test Int to readable with negative number`() {
        // Given
        val number = -987654321

        // When
        val readableNumber = number.toReadable()

        // Then
        assertEquals("-987 654 321", readableNumber)
    }
}
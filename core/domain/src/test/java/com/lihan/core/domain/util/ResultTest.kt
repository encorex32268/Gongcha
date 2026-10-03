package com.lihan.core.domain.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    private enum class TestError : Error {
        SOME_ERROR,
        ANOTHER_ERROR
    }

    @Test
    fun `map transforms Success data`() {
        val result: Result<Int, TestError> = Result.Success(10)
        val mapped = result.map { it * 2 }

        assertTrue(mapped is Result.Success)
        assertEquals(20, (mapped as Result.Success).data)
    }

    @Test
    fun `map preserves Error`() {
        val result: Result<Int, TestError> = Result.Error(TestError.SOME_ERROR)
        val mapped = result.map { it * 2 }

        assertTrue(mapped is Result.Error)
        assertEquals(TestError.SOME_ERROR, (mapped as Result.Error).error)
    }

    @Test
    fun `onSuccess executed only on Success`() {
        var called = false
        val success: Result<String, TestError> = Result.Success("hello")
        success.onSuccess {
            called = true
            assertEquals("hello", it)
        }
        assertTrue(called)

        var errorCalled = false
        val error: Result<String, TestError> = Result.Error(TestError.SOME_ERROR)
        error.onSuccess {
            errorCalled = true
        }
        assertTrue(!errorCalled)
    }

    @Test
    fun `onFailure executed only on Error`() {
        var called = false
        val error: Result<String, TestError> = Result.Error(TestError.SOME_ERROR)
        error.onFailure {
            called = true
            assertEquals(TestError.SOME_ERROR, it)
        }
        assertTrue(called)

        var successCalled = false
        val success: Result<String, TestError> = Result.Success("hello")
        success.onFailure {
            successCalled = true
        }
        assertTrue(!successCalled)
    }

    @Test
    fun `asEmptyResult converts Success to Unit`() {
        val success: Result<String, TestError> = Result.Success("hello")
        val emptyResult: EmptyResult<TestError> = success.asEmptyResult()

        assertTrue(emptyResult is Result.Success)
        assertEquals(Unit, (emptyResult as Result.Success).data)
    }
}

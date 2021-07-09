package com.feylabs.myviewmodel


import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class MainViewModelTest {

    @get:Rule
    var thrown = ExpectedException.none()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun init() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun calculate() {
        val width = "1.9"
        val length = "2"
        val height = "3"
        thrown.expect(NumberFormatException::class.java)
        thrown.expectMessage("For input string: \"2.4\"")
        mainViewModel.calculate(width, height, length)
        assertEquals(6, mainViewModel.result)
    }
}
package com.example.scratchcard.ui.cards

import app.cash.turbine.test
import com.example.scratchcard.MainDispatcherRule
import com.example.scratchcard.domain.generator.CodeGenerator
import com.example.scratchcard.domain.model.CardStatus
import com.example.scratchcard.domain.model.ScratchCard
import com.example.scratchcard.domain.usecase.ActivateCardUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ScratchCardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val activateCardUseCase: ActivateCardUseCase = mockk()
    private val codeGenerator: CodeGenerator = mockk()

    private lateinit var viewModel: ScratchCardViewModel

    @Before
    fun setUp() {
        viewModel = ScratchCardViewModel(activateCardUseCase, codeGenerator)
    }

    @Test
    fun `scratchCard should update uiState to Content with SCRATCHED status`() = runTest {
        val fakeCode = "test-code"
        every { codeGenerator.generate() } returns fakeCode

        viewModel.uiState.test {
            awaitItem() // Initial Content

            viewModel.scratchCard()

            assertTrue(awaitItem() is ScratchCardUiState.Loading)
            advanceTimeBy(2000)

            val successState = awaitItem() as ScratchCardUiState.Content
            assertEquals(fakeCode, successState.code)
            assertEquals(CardStatus.SCRATCHED, successState.status)
        }
    }

    @Test
    fun `cancelScratch should stop scratch operation and return to content state`() = runTest {
        every { codeGenerator.generate() } returns "any-code"

        viewModel.uiState.test {
            awaitItem() // Initial Content

            viewModel.scratchCard()
            awaitItem() // Loading

            viewModel.cancelScratch()
            assertTrue(awaitItem() is ScratchCardUiState.Content)

            advanceTimeBy(2001) // Ensure original coroutine would have finished
            expectNoEvents() // Verify no new state was emitted
        }
    }

    @Test
    fun `activateCard should update uiState to Content with ACTIVATED status on success`() = runTest {
        val fakeCode = "test-code"
        every { codeGenerator.generate() } returns fakeCode
        coEvery { activateCardUseCase(fakeCode) } coAnswers { 
            delay(1)
            ScratchCard(code = fakeCode, status = CardStatus.ACTIVATED)
        }

        viewModel.scratchCard()
        advanceTimeBy(2001)

        viewModel.uiState.test {
            awaitItem() // Consume SCRATCHED state

            viewModel.activateCard()

            assertTrue(awaitItem() is ScratchCardUiState.Loading)
            advanceTimeBy(1) // process the delay in mock

            val successState = awaitItem() as ScratchCardUiState.Content
            assertEquals(CardStatus.ACTIVATED, successState.status)
        }
    }

    @Test
    fun `activateCard should update uiState to Error on failure`() = runTest {
        val fakeCode = "test-code"
        val errorMessage = "Activation failed"
        every { codeGenerator.generate() } returns fakeCode
        coEvery { activateCardUseCase(fakeCode) } coAnswers { 
            delay(1)
            throw RuntimeException(errorMessage) 
        }

        viewModel.scratchCard()
        advanceTimeBy(2001)

        viewModel.uiState.test {
            awaitItem() // Consume SCRATCHED state

            viewModel.activateCard()

            assertTrue(awaitItem() is ScratchCardUiState.Loading)
            advanceTimeBy(1) // process the delay in mock

            val errorState = awaitItem() as ScratchCardUiState.Error
            assertTrue(errorState.message.contains(errorMessage))
        }
    }
}

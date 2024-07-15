package com.example.iceandfire

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.iceandfire.book.presentation.model.UrlBookArgs
import com.example.iceandfire.book.presentation.state.BookDetailsState
import com.example.iceandfire.book.presentation.view.BookDetailsActivity
import com.example.iceandfire.book.presentation.viewmodel.BookDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class BookDetailsInstrumentedTest {

    private lateinit var scenario: ActivityScenario<BookDetailsActivity>
    private val fakeState = MutableStateFlow(BookDetailsState())

    @Before
    fun setup() {
        setupKoinModules()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val args = UrlBookArgs("test_url")
        val intent = BookDetailsActivity.newIntent(context, args)
        scenario = ActivityScenario.launch(intent)
    }

    private fun setupKoinModules() {
        val bookDetailsModules = module {
            scope<BookDetailsActivity> { viewModel { stubBookDetailsViewModel() } }
        }
        loadKoinModules(bookDetailsModules)
    }

    private fun stubBookDetailsViewModel(): BookDetailsViewModel {
        return mockk(relaxed = true) {
            every { state } returns fakeState
        }
    }

    @Test
    fun testLoadingState() {
        scenario.onActivity {
            fakeState.value = BookDetailsState().showLoading()
        }

        onView(withId(R.id.loadingBookDetails)).check(matches(isDisplayed()))
    }
}

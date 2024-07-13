package com.example.iceandfire.book.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.presentation.model.UrlBookArgs
import com.example.iceandfire.book.presentation.viewmodel.BookDetailsViewModel
import com.example.iceandfire.core.extensions.getArguments
import com.example.iceandfire.core.extensions.putArguments
import com.example.iceandfire.core.extensions.showDialogError
import com.example.iceandfire.databinding.ActivityBookDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var args: UrlBookArgs
    private val bookDetailsViewModel: BookDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupArguments()
        setupToolbar()
        setupStateListener()
    }

    private fun setupArguments() {
        args = intent.getArguments() as UrlBookArgs
        bookDetailsViewModel.onScreenInitialized(args.url)
    }

    private fun setupToolbar() {
        binding.toolbarBookDetails.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupStateListener() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookDetailsViewModel.state.collect { state ->
                    loadingBookDetails.isVisible = state.isLoading
                    fabBookDetails.isVisible = state.isContent
                    containerDataBookDetails.isVisible = state.isContent
                    state.book?.let { book -> setBookData(book) }
                    state.isError?.let { throwable -> showError(throwable.message.orEmpty()) }
                }
            }
        }

    }

    private fun setBookData(book: Book) = with(binding) {
        itemDetailsNameBookDetail.setValue(book.name)
        itemDetailsAuthorBookDetail.setValue(book.authors[0])
        itemDetailsIsbnBookDetail.setValue(book.isbn)
        itemDetailsPublisherBookDetail.setValue(book.publisher)
        itemDetailsReleasedBookDetail.setValue(book.released)
        itemDetailsNumberOfPagesBookDetail.setValue(book.numberOfPages)
    }

    private fun showError(errorMessage: String) {
        this.showDialogError(errorMessage) {
            bookDetailsViewModel.onRetryClicked(args.url)
        }
    }

    companion object {
        fun newIntent(context: Context, args: UrlBookArgs): Intent {
            return Intent(context, BookDetailsActivity::class.java).apply { putArguments(args) }
        }
    }
}
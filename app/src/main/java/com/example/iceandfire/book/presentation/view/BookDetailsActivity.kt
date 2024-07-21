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

    private val bookDetailsViewModel: BookDetailsViewModel by viewModel()
    private val args: UrlBookArgs by lazy { intent.getArguments() as UrlBookArgs }
    private val binding: ActivityBookDetailsBinding by lazy {
        ActivityBookDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupArguments()
        setupToolbar()
        setupStateListener()
    }

    private fun setupArguments() {
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
        itemDetailsIsbnBookDetail.setValue(book.isbn)
        itemDetailsPublisherBookDetail.setValue(book.publisher)
        itemDetailsReleasedBookDetail.setValue(book.released)
        itemDetailsNumberOfPagesBookDetail.setValue(book.numberOfPages)
        if (book.authors.isNotEmpty()) itemDetailsAuthorBookDetail.setValue(book.authors[0])
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
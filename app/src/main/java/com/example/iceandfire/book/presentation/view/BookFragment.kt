package com.example.iceandfire.book.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.iceandfire.book.domain.model.Book
import com.example.iceandfire.book.presentation.action.BookAction
import com.example.iceandfire.book.presentation.adapter.BookListAdapter
import com.example.iceandfire.book.presentation.model.UrlBookArgs
import com.example.iceandfire.book.presentation.viewmodel.BookViewModel
import com.example.iceandfire.core.extensions.showDialogError
import com.example.iceandfire.databinding.FragmentBookBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFragment : Fragment() {

    private val bookViewModel: BookViewModel by viewModel()
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        setupStateListener()
        setupActionListener()

        return binding.root
    }

    private fun setupStateListener() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookViewModel.state.collect { state ->
                    loadingBook.isVisible = state.isLoading
                    recyclerViewBook.isVisible = state.isContent
                    state.bookList?.let { bookList -> setupBookRecyclerView(bookList) }
                    state.isError?.let { throwable -> showError(throwable.message.orEmpty()) }
                }
            }
        }
    }

    private fun setupBookRecyclerView(bookList: List<Book>) {
        val bookListAdapter = BookListAdapter(bookList) { url ->
            bookViewModel.onBookItemClicked(url)
        }
        binding.recyclerViewBook.adapter = bookListAdapter
    }

    private fun setupActionListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookViewModel.action.collect { action ->
                    if (action is BookAction.NavigateToBookDetails) navigateToBookDetails(action.url)
                }
            }
        }
    }

    private fun navigateToBookDetails(url: String) {
        val args = UrlBookArgs(url)
        val intent = BookDetailsActivity.newIntent(requireContext(), args)
        startActivity(intent)
        bookViewModel.onNavigationActivated()
    }

    private fun showError(errorMessage: String) {
        requireContext().showDialogError(errorMessage) {
            bookViewModel.onRetryClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
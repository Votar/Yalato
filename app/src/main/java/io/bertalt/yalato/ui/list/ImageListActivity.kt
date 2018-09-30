package io.bertalt.yalato.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import io.bertalt.yalato.R
import io.bertalt.yalato.api.response.PhotoRest
import io.bertalt.yalato.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class ImageListActivity : BaseActivity<ImageListContract.View, ImageListContract.ViewModel>(), ImageListContract.View {


    private val onPhotoClickListener = object : ListPhotoAdapter.OnItemClickListener {
        override fun photoClicked(photo: PhotoRest) {
            showDetails(photo)
        }
    }
    private val adapter get() = activity_list_photo_images_list_recycler.adapter as ListPhotoAdapter
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setupLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(searchCallbackListener)
        return true
    }

    override fun subscribe() {
        disposables.addAll(
                getViewModel().getListObservable().subscribe { adapter.swapData(it) },
                getViewModel().getLoadingSubject().subscribe { },
                getViewModel().getMessageObservable().subscribe { showMessage(it) }
        )
    }

    private fun setupLayout() {
        activity_list_photo_swipe_refresh.setOnRefreshListener(refreshListener)
        activity_list_photo_try_again_bth.setOnClickListener { getViewModel().onRetryClick() }
        activity_list_photo_images_list_recycler.layoutManager = LinearLayoutManager(this)
        activity_list_photo_images_list_recycler.adapter = ListPhotoAdapter(onPhotoClickListener)
    }

    override fun showProgress() {
        activity_list_photo_swipe_refresh.isRefreshing = true
    }

    override fun hideProgress() {
        activity_list_photo_swipe_refresh.isRefreshing = false
    }

    override fun showEmptyView() {
        activity_list_photo_empty_view.visibility = View.VISIBLE
        activity_list_photo_images_list_recycler.visibility = View.GONE
    }

    override fun hideEmptyView() {
        activity_list_photo_empty_view.visibility = View.GONE
        activity_list_photo_images_list_recycler.visibility = View.VISIBLE
    }

    override fun bindResult(result: List<PhotoRest>) {
        adapter.swapData(result)
    }

    override fun getRootView(): View = activity_list_photo_main_content

    override fun getViewModel(): ImageListContract.ViewModel = ViewModelProviders.of(this).get(ImageListViewModel::class.java)

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        getViewModel().onRefresh()
    }

    private val searchCallbackListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            getViewModel().submitSearch(query)
            searchView.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }

    }


    private fun showDetails(photo: PhotoRest) {
//        val intent = DetailsActivity.getIntent(this, photo)
//        startActivity(intent)
    }


}

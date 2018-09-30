package io.bertalt.yalato.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import io.bertalt.yalato.R
import io.bertalt.yalato.api.response.PhotoRest
import io.bertalt.yalato.base.BaseActivity
import io.bertalt.yalato.utils.beGone
import io.bertalt.yalato.utils.beVisible
import io.bertalt.yalato.utils.beVisibleIf
import kotlinx.android.synthetic.main.activity_main.*

class ImageListActivity : BaseActivity<ImageListContract.View, ImageListContract.ViewModel>(), ImageListContract.View {

    val vProgress get() = list_photo_progress

    private val onPhotoClickListener = object : ListPhotoAdapter.OnItemClickListener {
        override fun photoClicked(photo: PhotoRest) {
            showDetails(photo)
        }
    }
    private val adapter get() = photos_recycler.adapter as ListPhotoAdapter
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
                getViewModel().getLoadingObservable().subscribe { show -> vProgress.beVisibleIf(show) },
                getViewModel().getListObservable().subscribe {
                    if(it.isEmpty())
                        showEmptyView()
                    else{
                        hideEmptyView()
                        adapter.swapData(it)
                    }
                })
    }

    private fun setupLayout() {
        activity_list_photo_try_again_bth.setOnClickListener { getViewModel().onRetryClick() }
        photos_recycler.layoutManager = LinearLayoutManager(this)
        photos_recycler.adapter = ListPhotoAdapter(onPhotoClickListener)
        photos_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                (recyclerView.layoutManager as LinearLayoutManager).let {
                    getViewModel().onScrollStateChanged(
                            it.itemCount,
                            it.findLastCompletelyVisibleItemPosition(),
                            newState
                    )
                }

            }
        })

    }

    fun showEmptyView() {
        activity_list_photo_empty_view.beVisible()
        photos_recycler.beGone()
    }

    fun hideEmptyView() {
        activity_list_photo_empty_view.beGone()
        photos_recycler.beVisible()
    }

    override fun getRootView(): View = activity_list_photo_main_content

    override fun getViewModel(): ImageListContract.ViewModel = ViewModelProviders.of(this).get(ImageListViewModel::class.java)

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

package com.example.noodoe.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.noodoe.R
import com.example.noodoe.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 請參考下圖二頁面設計，實作一個清單頁面，顯示台北市政府開放資料 - 即時交通訊息。
上方請新增 Toolbar ，並新增 Time Zone 的按鈕，可以切換到 Time Zone 的頁面。
請使用 Fragment 實現。
請使用下列網址取得資料 https://tcgbusfs.blob.core.windows.net/dotapp/news.json
 */
class ListFragment : BaseFragment<ListViewModel>() {
    override val viewModel: ListViewModel by sharedViewModel()

    private val newsListAdapter by lazy {
        NewsListAdapter(NewsListAdapter.OnClickListener {
            it?.let {
                Toast.makeText(context, it.startTime, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false).apply {
            viewModel.getListInfo()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
        rv_news.adapter = newsListAdapter
    }

    private fun initObserver() {
        viewModel.listResultList.observe(viewLifecycleOwner) {
            it?.let {
                newsListAdapter.addFooterAndSubmitList(it)
            }
        }
    }

}
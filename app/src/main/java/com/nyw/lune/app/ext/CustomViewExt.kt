package com.nyw.lune.app.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.nyw.lib_base.base.appContext
import com.nyw.lib_base.ext.util.toHtml
import com.nyw.lune.R
import com.nyw.lune.app.network.stateCallback.ListDataUiState
import com.nyw.lune.app.util.SettingUtil
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.app.weight.loadsir.core.LoadSir
import com.nyw.lune.app.weight.loadsir.loadCallBack.EmptyCallback
import com.nyw.lune.app.weight.loadsir.loadCallBack.ErrorCallback
import com.nyw.lune.app.weight.loadsir.loadCallBack.LoadingCallback
import com.nyw.lune.app.weight.recyclerview.DefineLoadMoreView
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_study_with.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


/**
 * ?????????: YuWen Nie
 * ?????????: 2020/2/20
 * ?????????:????????????????????????????????????
 */


fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * ??????????????????
 * @param message ?????????????????????????????????
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}


fun LoadService<*>.setEmptyText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(EmptyCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.empty_text).text = message
        }
    }
}



fun LoadService<*>.setShowEmptyTip(flag: Boolean) {
    this.setCallBack(EmptyCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.empty_tip).visibility =if (flag) View.VISIBLE else View.GONE
    }
}

/**
 * ???????????????
 */
fun LoadService<*>.showEmpty(message: String = "",showTip:Boolean=true) {
    this.setEmptyText(message)
    this.setShowEmptyTip(showTip)
    this.showCallback(EmptyCallback::class.java)
}

/**
 * ???????????????
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadsir = LoadSir.getDefault().register(view) {
        //??????????????????????????????
        callback.invoke()
    }
    loadsir.showSuccess()
    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadsir)
    return loadsir
}

//???????????????Recyclerview
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

//??????SwipeRecyclerView
fun SwipeRecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.initFooter(loadmoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView(appContext)
    //?????????????????????
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    //????????????????????????
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadmoreListener.onLoadMore()
    })
    this.run {
        //????????????????????????
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //????????????????????????
        setLoadMoreListener(loadmoreListener)
    }
    return footerView
}

fun RecyclerView.initFloatBtn(floatbtn: FloatingActionButton) {
    //??????recyclerview?????????????????????????????????????????????????????????????????????
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatbtn.visibility = View.INVISIBLE
            }
        }
    })
    floatbtn.backgroundTintList = SettingUtil.getOneColorStateList(appContext)
    floatbtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //????????????recyclerview ?????????????????????????????????????????????40????????????????????????????????????????????????????????????????????????
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//?????????????????????????????????(??????)
        } else {
            smoothScrollToPosition(0)//??????????????????????????????(?????????)
        }
    }
}

//????????? SwipeRefreshLayout
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //??????????????????
        setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}

/**
 * ??????????????????toolbar ???????????????
 */
fun TitleBar.init(titleStr: String = ""): TitleBar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr
    return this
}

/**
 * ????????????????????????toolbar
 */
fun TitleBar.initClose(
    leftTitleStr: String = "",
    rithtTitleStr: String = "",
    backImg: Int = R.drawable.ic_arrow_back,
    onBack: (clickType: Int?) -> Unit
): TitleBar {
    setBackgroundColor(ColorUtils.getColor(R.color.colorEF))
    setLeftIcon(backImg)
    leftTitle = leftTitleStr.toHtml()
    rightTitle = rithtTitleStr.toHtml()
    setLeftTitleSize(10f)
    setLeftIconTint(R.color.colorBlack333)
    setLeftTitleColor(R.color.colorBlack333)
    setOnTitleBarListener(object : OnTitleBarListener {
        override fun onLeftClick(titleBar: TitleBar?) {
            super.onLeftClick(titleBar)
            onBack.invoke(0)//?????????????????????
        }

        override fun onRightClick(titleBar: TitleBar?) {
            super.onRightClick(titleBar)
            onBack.invoke(1)//??????
        }
    })
    return this
}

/**
 * ????????????????????????????????????????????????????????????????????? ????????????????????????????????????????????? Textview???FragmentLayout???????????????????????????
 * ???????????????BottomNavigationViewEx????????????????????????FragmentLayout???????????? is Fragmentlayout????????? is BottomNavigationViewEx??????
 * ??????????????????????????? is FragmentLayout???????????? ?????? is BottomNavigationViewEx???????????????
 */
fun setUiTheme(color: Int, vararg anyList: Any?) {
    anyList.forEach { view ->
        view?.let {
            when (it) {
                is LoadService<*> -> SettingUtil.setLoadingColor(color, it as LoadService<Any>)
                is FloatingActionButton -> it.backgroundTintList =
                    SettingUtil.getOneColorStateList(color)
                is SwipeRefreshLayout -> it.setColorSchemeColors(color)
                is DefineLoadMoreView -> it.setLoadViewColor(SettingUtil.getOneColorStateList(color))
                is Toolbar -> it.setBackgroundColor(color)
                is TextView -> it.setTextColor(color)
                is LinearLayout -> it.setBackgroundColor(color)
                is ConstraintLayout -> it.setBackgroundColor(color)
                is FrameLayout -> it.setBackgroundColor(color)
            }
        }
    }
}

//??????????????????????????????
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //??????0????????????????????? ????????????
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun MagicIndicator.bindViewPager2(
    viewPager: ViewPager2,
    mStringList: List<String> = arrayListOf(),
    action: (index: Int) -> Unit = {}
) {
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.isAdjustMode=true
    commonNavigator.adapter = object : CommonNavigatorAdapter() {

        override fun getCount(): Int {
            return  mStringList.size
        }
        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            val simplePagerTitleView = SimplePagerTitleView(context)
            simplePagerTitleView.text = mStringList.get(index)
            simplePagerTitleView.normalColor = Color.parseColor("#333333")
            simplePagerTitleView.selectedColor = Color.parseColor("#ffffff")
            simplePagerTitleView.setOnClickListener { viewPager.currentItem = index }
            return simplePagerTitleView
        }
        override fun getIndicator(context: Context): IPagerIndicator {
            val indicator = LinePagerIndicator(context)
            val navigatorHeight = context.resources.getDimension(R.dimen.dp_40)
            indicator.lineHeight = navigatorHeight
            indicator.setColors(Color.parseColor("#26D4A1"))
            return indicator
        }
    }
    this.navigator = commonNavigator

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager2.onPageSelected(position)
            action.invoke(position)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            this@bindViewPager2.onPageScrollStateChanged(state)
        }
    })
}

fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //???????????????
    this.isUserInputEnabled = isUserInputEnabled
    //???????????????
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}


/**
 * ???????????????
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * ??????????????????
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        //??????
        when {
            //???????????????????????? ?????????????????????
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //????????????
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            //???????????????
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        //??????
        if (data.isRefresh) {
            //??????????????????????????????????????????????????????????????????
            loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}